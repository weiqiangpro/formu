package com.formu.Control;

import com.formu.Service.imp.UserService;
import com.formu.Utils.*;
import com.formu.bean.Info;
import com.formu.bean.Token;
import com.formu.bean.TokenStatus;
import com.formu.bean.User;
import com.formu.mapper.UserMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @ApiOperation(value = "登录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "passwd", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Msg login(@RequestParam("user") String username, @RequestParam("passwd") String passwd) {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(passwd)) {
            User user1 = userMapper.selectLoginByAccount(username);
            if (user1 != null && StringUtils.isBlank(user1.getYiban()) && StringUtils.isNotBlank(user1.getPasswd())) {
                if (user1.getPasswd().equals(Md5Utils.md5(passwd))) {
                    try {

                        String token = JwtToken.createToken();
                        Map<String, String> map = new HashMap<>();

                        map.put("mes", "登陆成功");
                        map.put("Token", token);

                        User user = new User();
                        user.setUserId(1);
                        user.setAccount(username);

                        String json = JsonUtil.obj2String(user);

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
    @ResponseBody
    public Msg a(HttpServletRequest request) {

        Map<String, String[]> params = request.getParameterMap();
        String code = params.get("code")[0];

        if (StringUtils.isBlank(code))
            return Msg.createByErrorMessage("易班登录失败");

        Map<String, String> access_tokenMap = new HashMap<>();
        access_tokenMap.put("client_id", "9d8b2a825cf5677a");
        access_tokenMap.put("client_secret", "68668685130aa1a94d0b3a3de3f1b1f8");
        access_tokenMap.put("code", code);
        access_tokenMap.put("redirect_uri", "http://weiqiang:8080/auth");
        String access_tokenJson = HttpClientUtil.doPost("https://openapi.yiban.cn/oauth/access_token", access_tokenMap);
        Token access_token = JsonUtil.string2Obj(access_tokenJson, Token.class);

        if (access_token == null || StringUtils.isBlank(access_token.getAccess_token()))
            return Msg.createByErrorMessage("易班登录失败");


        Map<String, String> meMap = new HashMap<>();
        meMap.put("access_token", access_token.getAccess_token());
        String s1 = HttpClientUtil.doGet("https://openapi.yiban.cn/user/me", meMap);
        TokenStatus me = JsonUtil.string2Obj(s1, TokenStatus.class);


        if (me == null || !"success".equals(me.getStatus()))
            return Msg.createByErrorMessage("易班登录失败");

        String token = null;
        try {
            token = JwtToken.createToken();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("mes", "登陆成功");
        map.put("Token", token);

        Info info = me.getInfo();
        String yiban = info.getYb_userid();
        User YBUser = userMapper.selectByYB(yiban);

        if (YBUser != null) {
            if (StringUtils.isBlank(YBUser.getYiban()))
                return Msg.createByErrorMessage("易班登录失败");
            User newYB = new User();
            newYB.setUserId(YBUser.getUserId());
            newYB.setAccount(null);
            String json = JsonUtil.obj2String(newYB);
            redis.opsForValue().set(token, json, 30, TimeUnit.MINUTES);
            return Msg.createBySuccess(map);
        }


        User newYbuser = new User();
        newYbuser.setUserName(info.getYb_username());
        newYbuser.setPho(info.getYb_userhead());
        newYbuser.setYiban(info.getYb_userid());
        userMapper.insertSelective(newYbuser);
        User user = userMapper.selectByYB(info.getYb_userid());
        user.setAccount(null);
        String json = JsonUtil.obj2String(user);
        redis.opsForValue().set(token, json, 30, TimeUnit.MINUTES);
        return Msg.createBySuccess(map);
    }

    @RequestMapping("/YB/login")
    public String YBlogin() {
        String client_id = "9d8b2a825cf5677a";
        String redirect_url = "http://weiqiang:8080/auth";
        String url = "https://openapi.yiban.cn/oauth/authorize?client_id=" + client_id + "&redirect_uri=" + redirect_url + "&state=STATE";
        return "redirect:" + url;
    }


}
