package com.formu.Control;

import com.formu.Service.imp.UserService;
import com.formu.Utils.JsonUtil;
import com.formu.Utils.JwtToken;
import com.formu.Utils.Md5Utils;
import com.formu.Utils.Msg;
import com.formu.bean.User;
import com.formu.mapper.UserMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by weiqiang
 */
@RestController
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
    public Msg login(@RequestParam("user") String username, @RequestParam("passwd") String passwd) {
        if (username != null && passwd != null) {
            User user1 = userMapper.selectLoginByAccount(username);
            if (user1 != null && StringUtils.isNotBlank(user1.getPasswd())) {
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
                }else
                    return Msg.createByErrorMessage("密码错误");
            } else
                return  Msg.createByErrorMessage("不存在该用户");
        }
        return Msg.createByError();
    }
}
