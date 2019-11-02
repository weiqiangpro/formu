package com.formu.Service.imp;

import com.formu.Service.IUserService;
import com.formu.Utils.Msg;
import com.formu.bean.vo.Follow;
import com.formu.bean.vo.User;
import com.formu.bean.po.FollowInfo;
import com.formu.bean.po.OtherPo;
import com.formu.bean.po.UserPo;
import com.formu.mapper.FollowMapper;
import com.formu.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

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
    public Msg getOtherById(int otherId, int userId) {
        User user = userMapper.selectByPrimaryKey(otherId);
        Follow follow = followMapper.selectByMeAndOther(userId, otherId);
        if (user != null) {
            return Msg.createBySuccess(new OtherPo(user, follow != null));
        }
        return Msg.createByErrorMessage("没有此用户");
    }

    @Override
    public Msg getMyByid(int id) {
        User user = userMapper.selectByPrimaryKey(id);

        if (user != null) {
            return Msg.createBySuccess(new UserPo(user));
        }
        return Msg.createByErrorMessage("没有此用户");
    }

    @Override
    public Msg isregister(String accout) {
        String pattern = "[a-zA-Z0-9]+";
        if (!Pattern.compile(pattern).matcher(accout).matches() || accout.length() < 6 || accout.length() > 20)
            return Msg.createByErrorMessage("账号不符合规则");

        User user = userMapper.selectByAccount(accout);

        if (user != null)
            return Msg.createByErrorMessage("该账号已存在");
        return Msg.createBySuccessMessage("该账号可以注册");
    }

    @Override
    public Msg modifyInformation(User user) {
        if (user == null)
            return Msg.createBySuccess("修改失败");
        int ok = userMapper.updateByPrimaryKeySelective(user);
        if (ok > 0)
            return Msg.createBySuccessMessage("信息修改成功");
        return Msg.createBySuccess("修改失败");
    }

    @Override
    public Msg register(User user, String code) {
        String pattern = "[a-zA-Z0-9]+";
        String account = user.getAccount();
        if (!Pattern.compile(pattern).matcher(account).matches() || account.length() < 6 || account.length() > 20)
            return Msg.createByErrorMessage("账号不符合规则");

        if (user.getPasswd().length() < 6 || user.getPasswd().length() > 30)
            return Msg.createByErrorMessage("密码的位数不得小于6或者大于30");

        User user1 = userMapper.selectByAccount(user.getAccount());
        if (user1 != null)
            return Msg.createByErrorMessage("已存在该用户");

        String code1 = redis.opsForValue().get(user.getAccount());
//        String code1 = "123";

        if (code == null)
            return Msg.createByErrorMessage("验证码不能为空");
        if (code1 == null || !code1.equals(code))
            return Msg.createByErrorMessage("验证码错误");

        int ok = userMapper.insertSelective(user);
        if (ok > 0) {
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
                    if (newpasswd1.length() < 6 || newpasswd1.length() > 30)
                        return Msg.createByErrorMessage("密码的位数不得小于6或者大于30");
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
        if (StringUtils.isBlank(passwd1) || StringUtils.isBlank(passwd2))
            return Msg.createByErrorMessage("输入密码不能为空");
        if (!passwd1.equals(passwd2))
            return Msg.createByErrorMessage("两次密码输入不一致");
        String codeRedis = redis.opsForValue().get(account);
        if (code == null || !code.equals(codeRedis))
            return Msg.createByErrorMessage("验证码输入错误");
        if (passwd1.length() < 6 || passwd1.length() > 30)
            return Msg.createByErrorMessage("密码的位数不得小于6或者大于30");
        User user = new User();
        user.setAccount(account);
        user.setPasswd(passwd1);
        int ok = userMapper.updateByAccount(user);
        if (ok > 0)
            return Msg.createBySuccessMessage("密码找回成功");
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
            int ok3 = userMapper.updateFollowedNumById(otherId, 1);
            if (ok1 > 0 && ok2 > 0 && ok3 > 0)
                return Msg.createBySuccessMessage("关注成功!");
            else
                return Msg.createBySuccessMessage("关注失败!");
        } else {
            int ok1 = followMapper.deleteByPrimaryKey(follow.getFollowId());
            int ok2 = userMapper.updateFollowNumById(otherId, -1);
            int ok3 = userMapper.updateFollowedNumById(otherId, -1);
            if (ok1 > 0 && ok2 > 0 && ok3 > 0)
                return Msg.createBySuccessMessage("取消关注成功!");
            else
                return Msg.createBySuccessMessage("关注失败!");
        }

    }

    @Override
    public Msg getFollows(int userid) {

        List<FollowInfo> follows = followMapper.getFollowsByUserId(userid);

        if (follows.size() > 0) {
            return Msg.createBySuccess(follows);
        }
        return Msg.createByErrorMessage("未找到好友");
    }

    @Override
    public Msg getFolloweds(int userid) {

        List<FollowInfo> followeds = followMapper.getFollowedsByUserId(userid);

        if (followeds.size() > 0) {
            return Msg.createBySuccess(followeds);
        }
        return Msg.createByErrorMessage("未找到好友");
    }


    public Msg logout(HttpServletRequest request) {
        String token = request.getHeader("Token");
        Boolean delete = redis.delete(token);
        if (delete)
            return Msg.createBySuccessMessage("退出登录成功");
        return Msg.createByErrorMessage("退出登录成功");
    }
}


