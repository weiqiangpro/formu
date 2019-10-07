package com.formu.Control;

import com.formu.Service.imp.UserService;
import com.formu.Utils.Md5Utils;
import com.formu.Utils.Msg;
import com.formu.bean.User;
import com.formu.common.Common;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/user/")
public class UserControl {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private Common common;

    //获取别人的信息
    @RequestMapping("other/{id}")
    public Msg getother(@PathVariable("id") int userid) {
        return userService.getOtherById(userid);
    }

    //获取自己的信息
    @RequestMapping(value = "me.do", method = RequestMethod.GET)
    public Msg getme(HttpServletRequest request) {
        return userService.getMyByid(common.getid(request));
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Msg register(@RequestParam(value = "account", required = true) String accout,
                        @RequestParam(value = "name", required = true) String name,
                        @RequestParam(value = "passwd1", required = true) String passwd1,
                        @RequestParam(value = "passwd2", required = true) String passwd2,
                        @RequestParam(value = "email", required = true) String email,
                        @RequestParam(value = "pho", defaultValue = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570366405712&di=3065c180a67931b0acf277316abfd4c4&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F015d9b56ac5a2d6ac7256cb0ece272.png") String pho,
                        @RequestParam(value = "code", required = true) String code, HttpServletRequest request) {
        if (passwd1.equals(passwd2)) {
            User user = new User();
            user.setAccount(accout);
            user.setUserName(name);
            user.setPasswd(Md5Utils.md5(passwd1));
            user.setEmail(email);
            user.setPho(pho);
            return userService.register(user, code);
        }
        return Msg.createByErrorMessage("两次密码不一致");
    }

    //注册时候用的发送邮件
    @RequestMapping(value = "registeremail", method = RequestMethod.POST)
    public Msg sendRegister(@RequestParam("email") String email,
                            @RequestParam("account") String account) {
        Long expire = redis.getExpire(account, TimeUnit.SECONDS);
        if (expire > 120) {
            return Msg.createByErrorMessage("请60s后再发送邮件");
        }
        if (common.registerEmail(email, account)) {
            return Msg.createBySuccessMessage("邮件发送成功");
        }
        return Msg.createByErrorMessage("邮件发送失败");
    }

    //找回密码时候的发送邮件
    @RequestMapping(value = "findemail", method = RequestMethod.POST)
    public Msg sendFind(@RequestParam("account") String account) {
        Long expire = redis.getExpire(account, TimeUnit.SECONDS);
        if (expire > 120) {
            return Msg.createByErrorMessage("请60s后再发送邮件");
        }
        if (common.findEmail(account)) {
            return Msg.createBySuccessMessage("邮件发送成功");
        }
        return Msg.createByErrorMessage("邮件发送失败");
    }

    //修改密码
    @RequestMapping(value = "modifypd.do", method = RequestMethod.PUT)
    public Msg updatepasswd(@RequestParam("oldPasswd") String oldpasswd,
                            @RequestParam("newPasswd1") String newpasswd1,
                            @RequestParam("newPasswd2") String newpasswd2,
                            HttpServletRequest request) {
        return userService.updatepasswd(oldpasswd, newpasswd1, newpasswd2, common.getid(request));
    }

    //找回密码
    @RequestMapping(value = "findpd", method = RequestMethod.PUT)
    public Msg findpasswd(@RequestParam("account") String account,
                          @RequestParam("passwd1") String passwd1,
                          @RequestParam("passwd2") String passwd2,
                          @RequestParam("code") String code,
                          HttpServletRequest request) {
        return userService.findpasswd(account, passwd1, passwd2, code);
    }

    //获取邮箱   带*******的邮箱
    @RequestMapping(value = "getemail", method = RequestMethod.GET)
    public Msg getbyaccout(@RequestParam("accout") String accout) {
        return userService.getbyaccout(accout);
    }

    //关注
    @RequestMapping(value = "follow/{userid}", method = RequestMethod.POST)
    public Msg follow(@PathVariable("userid") int userid, HttpServletRequest request) {
        return userService.addFollow(userid, common.getid(request));
    }

    //获取关注的人
    @RequestMapping(value = "friends.do", method = RequestMethod.GET)
    public Msg getfriends(HttpServletRequest request) {
        return userService.getFriends(common.getid(request));
    }
}
