package com.formu.Service.imp;

import com.formu.Service.IUserService;
import com.formu.Utils.Msg;
import com.formu.Utils.SetUtil;
import com.formu.bean.ArticleGood;
import com.formu.bean.CommentGood;
import com.formu.bean.Follow;
import com.formu.bean.User;
import com.formu.bean.po.FollowInfo;
import com.formu.bean.po.UserPo;
import com.formu.mapper.FollowMapper;
import com.formu.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by weiqiang
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private FollowMapper followMapper;

    @Override
    public Msg getOtherById(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            return Msg.createBySuccess(new UserPo(user,false));
        }
        return Msg.createByErrorMessage("没有此用户");
    }

    @Override
    public Msg getMyByid(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            return Msg.createBySuccess(new UserPo(user,true));
        }
        return Msg.createByErrorMessage("没有此用户");
    }

    @Override
    public Msg register(User user, String code) {
        User user1 = userMapper.selectByAccount(user.getAccount());
        if (user1 != null)
            return Msg.createByErrorMessage("已存在该用户");
//        String code1 = redis.opsForValue().get(user.getAccount());
        String code1 = "123";
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
    public Msg addFollow(int otherId, int userId) {
        if (userMapper.selectByPrimaryKey(otherId) == null)
            return Msg.createByErrorMessage("关注失败,没有该用户");

        Follow follow = followMapper.selectByMeAndOther(userId, otherId);
        if (follow == null) {
            int ok1 = followMapper.insertSelective(new Follow(null, otherId, userId));
            int ok2 = userMapper.updateFollowNumById(otherId, 1);
            if (ok1 > 0 && ok2 > 0)
                return Msg.createBySuccessMessage("关注成功！");
            else
                return Msg.createBySuccessMessage("关注失败！");
        } else {
            int ok3 = followMapper.deleteByPrimaryKey(follow.getFollowId());
            int ok4 = userMapper.updateFollowNumById(otherId, -1);
            if (ok3 > 0 && ok4 > 0)
                return Msg.createBySuccessMessage("取消关注成功！");
            else
                return Msg.createBySuccessMessage("关注失败！");
        }

    }

    @Override
    public Msg getFriends(int userid) {

        List<FollowInfo> frends = followMapper.getFrendsByUserId(userid);

        if (frends.size() > 0){
            return Msg.createBySuccess(frends);
        }
        return Msg.createByErrorMessage("未找到好友");
    }

}


