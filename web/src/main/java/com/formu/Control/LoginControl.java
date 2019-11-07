package com.formu.Control;

import com.formu.Utils.*;
import com.formu.bean.yb.Info;
import com.formu.bean.yb.Token;
import com.formu.bean.yb.TokenStatus;
import com.formu.bean.vo.User;
import com.formu.mapper.UserMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by weiqiang
 */
@Controller
@CrossOrigin
public class LoginControl {

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "登录" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "passwd", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Msg login(@RequestParam("user") String username, @RequestParam("passwd") String passwd) {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(passwd)) {
            User user1 = userMapper.selectByAccount(username);
            if (user1 != null && StringUtils.isBlank(user1.getYiban()) && StringUtils.isNotBlank(user1.getPasswd())) {
                passwd = Md5Utils.md5(passwd);
                if (user1.getPasswd().equals(passwd)) {
                    try {

                        String token = JwtToken.createToken();
                        Map<String, String> map = new HashMap<>();

                        map.put("mes", "登陆成功");
                        map.put("Token", token);

                       user1.setPasswd(null);
                        String json = JsonUtil.obj2String(user1);
                        redis.opsForValue().set(token, json, 30, TimeUnit.MINUTES);
                        return Msg.createBySuccess(map);
                    } catch (Exception e) {
                        return Msg.createByError();
                    }
                } else
                    return Msg.createByErrorMessage("密码错误");
            } else
                return Msg.createByErrorMessage("不存在该用户");
        }
        return Msg.createByError();
    }


    @RequestMapping("auth")
    public String  auth(HttpServletRequest request, Model model, HttpServletResponse response) throws IOException, ServletException {

        Map<String, String[]> params = request.getParameterMap();
        String code = params.get("code")[0];

        if (StringUtils.isBlank(code))
            return "ybsuccess";

        Map<String, String> access_tokenMap = new HashMap<>();
        access_tokenMap.put("client_id", "e0a9836f8550a256");
        access_tokenMap.put("client_secret", "8a71e64358ac5cb03e6aeefee02bbf5e");
        access_tokenMap.put("code", code);
        access_tokenMap.put("redirect_uri", "https://www.aoteam.top/api/auth");
        String access_tokenJson = HttpClientUtil.doPost("https://openapi.yiban.cn/oauth/access_token", access_tokenMap);
        Token access_token = JsonUtil.string2Obj(access_tokenJson, Token.class);

        if (access_token == null || StringUtils.isBlank(access_token.getAccess_token()))
            return "ybsuccess";


        Map<String, String> meMap = new HashMap<>();
        meMap.put("access_token", access_token.getAccess_token());
        String s1 = HttpClientUtil.doGet("https://openapi.yiban.cn/user/me", meMap);
        TokenStatus me = JsonUtil.string2Obj(s1, TokenStatus.class);


        if (me == null || !"success".equals(me.getStatus()))
            return "ybsuccess";

        String token = null;
        try {
            token = JwtToken.createToken();

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("Token",token);

        Info info = me.getInfo();
        String yiban = info.getYb_userid();
        User YBUser = userMapper.selectByYB(yiban);
        System.out.println(YBUser);
        if (YBUser != null) {
            if (StringUtils.isBlank(YBUser.getYiban()))
                response.getWriter().write("cuowu");
            String json = JsonUtil.obj2String(YBUser);
            redis.opsForValue().set(token, json, 30, TimeUnit.MINUTES);
            return "ybsuccess";
        }


        User newYbuser = new User();
        newYbuser.setUserName(info.getYb_username());
        newYbuser.setYiban(info.getYb_userid());
        newYbuser.setPho(info.getYb_userhead());
        newYbuser.setSex(info.getYb_sex().equals("M")?0:1);
        newYbuser.setHome(".");
        newYbuser.setPerson(".");
        newYbuser.setFollowedNum(0);
        newYbuser.setFollowNum(0);
        userMapper.insertSelective(newYbuser);
        User user = userMapper.selectByYB(info.getYb_userid());
        String json = JsonUtil.obj2String(user);
        redis.opsForValue().set(token, json, 30, TimeUnit.MINUTES);
        return "ybsuccess";
    }

    @RequestMapping(value = "/YB/login",method = RequestMethod.GET)
    public String YBlogin() {
        String client_id = "e0a9836f8550a256";
        String redirect_url = "https://www.aoteam.top/api/auth";
        String url = "https://openapi.yiban.cn/oauth/authorize?client_id=" + client_id + "&redirect_uri=" + redirect_url + "&state=STATE";
        return "redirect:" + url;
    }



    @RequestMapping("/islogin")
    @ResponseBody
    public Msg islogin(){
        return Msg.createBySuccess();
    }


}
