package com.formu.Control;

import com.formu.Utils.JsonUtil;
import com.formu.Utils.JwtToken;
import com.formu.Utils.Msg;
import com.formu.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by weiqiang
 */
@RestController
public class LoginControl {

    @Autowired
    StringRedisTemplate redis;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg login(@RequestParam("user") String username) {
        if (username != null) {
            try {

                String token  = JwtToken.createToken();
                Map<String, String> map = new HashMap<>();

                map.put("mes", "登陆成功");
                map.put("Token", token);

                User user =  new User();
                user.setUserId(1);
                user.setAccount(username);
                user.setFriends("");

                String json = JsonUtil.obj2String(user);

                redis.opsForValue().set(token,json,30, TimeUnit.MINUTES);

                return Msg.createBySuccess(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Msg.createByError();
    }
}
