package com.formu.Service.imp;

import com.formu.Service.IUserService;
import com.formu.Utils.Msg;
import com.formu.Utils.SetUtil;
import com.formu.bean.User;
import com.formu.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

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

    @Override
    public Msg updatepasswd(String oldpasswd, String newpasswd1, String newpasswd2, int userid) {
        User user1 = userMapper.selectByPrimaryKey(userid);
        if (StringUtils.isNotBlank(user1.getYiban()))
            return Msg.createByErrorMessage("该用户为易班登录,无法修改密码");
        if (StringUtils.isNotBlank(user1.getPasswd())) {
            if (user1.getPasswd().equals(oldpasswd)) {
                if (newpasswd1.equals(newpasswd2)) {
                    User user = new User();
                    user.setUserId(userid);
                    user.setPasswd(newpasswd1);
                    userMapper.updateByPrimaryKeySelective(user);
                } else
                    return Msg.createByErrorMessage("两次密码输入不一致");
            } else
                return Msg.createByErrorMessage("原密码输入错误");
        }
        return Msg.createByError();

    }

    @Override
    public Msg findpasswd(String account, String passwd1, String passwd2, String code) {
        if (userMapper.selectByAccount(account) == null)
            return Msg.createByErrorMessage("没有该用户");
        if (StringUtils.isNotBlank(passwd1) || StringUtils.isNotBlank(passwd2))
            return Msg.createByErrorMessage("输入密码不能为空");
        if (!passwd1.equals(passwd2))
            return Msg.createByErrorMessage("两次密码输入不一致");
        if (StringUtils.isNotBlank(code) && code.equals(redis.opsForValue().get(account))) {
            User user = new User();
            user.setAccount(account);
            user.setPasswd(passwd1);
            int ok = userMapper.updateByAccount(user);
            if (ok > 0)
                return Msg.createBySuccessMessage("密码找回成功");
        }
        return Msg.createByErrorMessage("密码找回失败");

    }

    @Override
    public Msg getbyaccout(String accout) {
        User user = userMapper.selectByAccount(accout);
        if (user == null)
            return Msg.createByErrorMessage("不存在该用户");
        String oldEmail = user.getEmail();
        String email = StringUtils.substring(oldEmail, 0, 3) + "*******" + StringUtils.substringAfter(oldEmail, "@");
        return Msg.createByErrorMessage(email);
    }

    @Override
    public Msg addFollow(int otherid, int userid) {
        if (userMapper.selectByPrimaryKey(otherid) == null)
            return Msg.createByErrorMessage("关注失败,没有该用户");
        User user = userMapper.selectByPrimaryKey(userid);
        User newUser = new User();
        newUser.setUserId(userid);
        newUser.setFriends(SetUtil.upGood(user.getFriends(), otherid));
        if (user.getFriends().equals(newUser.getFriends()))
            return Msg.createBySuccessMessage("您已关注过该用户");
        int ok = userMapper.updateByPrimaryKeySelective(newUser);
        if (ok > 0)
            return Msg.createBySuccessMessage("关注成功");
        return Msg.createByErrorMessage("关注失败");
    }

}


