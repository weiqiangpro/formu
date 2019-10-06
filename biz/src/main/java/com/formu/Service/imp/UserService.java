package com.formu.Service.imp;

import com.formu.Service.IUserService;
import com.formu.Utils.Msg;
import com.formu.bean.User;
import com.formu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by weiqiang
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redis;

    @Override
    public Msg getOtherById(int id) {
        User user = userMapper.selectOtherByPrimaryKey(id);
        if (user != null) {
            return Msg.createBySuccess(user);
        }
        return Msg.createByErrorMessage("没有此用户");
    }

    @Override
    public Msg getMyByid(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            return Msg.createBySuccess(user);
        }
        return Msg.createByErrorMessage("没有此用户");
    }

    @Override
    public Msg register(User user, String code) {


        User user1 = userMapper.selectByAccount(user.getAccount());
        if (user1 != null)
            return Msg.createByErrorMessage("已存在该用户");
        String code1 = redis.opsForValue().get(user.getAccount());
        if (code1 != null && code1.equals(code)) {
            int ok = userMapper.insertSelective(user);
            if (ok > 0)
                redis.delete(user.getAccount());
                return Msg.createBySuccessMessage("注册成功");
        }


        return Msg.createByErrorMessage("验证码错误");
    }

}


