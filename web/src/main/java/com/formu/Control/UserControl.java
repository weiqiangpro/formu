package com.formu.Control;

import com.formu.Service.imp.UserService;
import com.formu.Utils.Md5Utils;
import com.formu.Utils.Msg;
import com.formu.bean.User;
import com.formu.common.Common;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@CrossOrigin
@RequestMapping("/user/")
public class UserControl {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private Common common;

    @ApiOperation(value = "获取别人的信息", notes = "")
    @ApiImplicitParam(name = "id", value = "用户的id", required = true, paramType = "path", dataType = "Integer")
    @RequestMapping(value = "other/{id}",method = RequestMethod.GET)
    public Msg getother(@PathVariable("id") int userid) {
        return userService.getOtherById(userid);
    }

    //获取自己的信息
    @RequestMapping(value = "me.do", method = RequestMethod.GET)
    public Msg getme(HttpServletRequest request) {
        return userService.getMyByid(common.getid(request));
    }

    @ApiOperation(value = "注册", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "passwd1", value = "第一次输入密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "passwd2", value = "第二次输入密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pho", value = "头像，有默认值", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String")
    })
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Msg register(@RequestParam(value = "account", required = true) String accout,
                        @RequestParam(value = "passwd1", required = true) String passwd1,
                        @RequestParam(value = "passwd2", required = true) String passwd2,
                        @RequestParam(value = "email", required = true) String email,
                        @RequestParam(value = "code", required = true) String code, HttpServletRequest request) {

        if (passwd1.equals(passwd2)) {
            User user = new User();
            user.setAccount(accout);
            user.setUserName(common.randomName());
            user.setPasswd(Md5Utils.md5(passwd1));
            user.setEmail(email);
            user.setPho(common.getHead());
            user.setFollowNum(0);
            return userService.register(user, code);
        }
        return Msg.createByErrorMessage("两次密码不一致");
    }

    @ApiOperation(value = "注册时使用的发送邮箱验证码,根据账号判断,每间隔60秒才能发送", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String"),
            @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String")
    })
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


    @ApiOperation(value = "修改密码,需要登录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPasswd", value = "老密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPasswd1", value = "新密码1", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPasswd2", value = "新密码2", required = true, dataType = "String")
    })
    @RequestMapping(value = "modifypd.do", method = RequestMethod.PUT)
    public Msg updatepasswd(@RequestParam("oldPasswd") String oldpasswd,
                            @RequestParam("newPasswd1") String newpasswd1,
                            @RequestParam("newPasswd2") String newpasswd2,
                            HttpServletRequest request) {
        if (common.isYB(request))
            return Msg.createByErrorMessage("易班用户不能修改密码");
        return userService.updatepasswd(oldpasswd, newpasswd1, newpasswd2, common.getid(request));
    }

    //找回密码
    @ApiOperation(value = "找回密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "passwd1", value = "新密码1", required = true, dataType = "String"),
            @ApiImplicitParam(name = "passwd2", value = "新密码2", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String")
    })
    @RequestMapping(value = "findpd", method = RequestMethod.PUT)
    public Msg findpasswd(@RequestParam("account") String account,
                          @RequestParam("passwd1") String passwd1,
                          @RequestParam("passwd2") String passwd2,
                          @RequestParam("code") String code,
                          HttpServletRequest request) {
        return userService.findpasswd(account, passwd1, passwd2, code);
    }

    @ApiOperation(value = "找回密码时使用的发送邮箱验证码,根据账号判断,每间隔60秒才能发送", notes = "")
    @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String")
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

    //获取邮箱   带*******的邮箱
    @ApiOperation(value = "获取邮箱,带*******的邮箱,再找回密码的时候使用", notes = "")
    @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String")
    @RequestMapping(value = "getemail", method = RequestMethod.GET)
    public Msg getbyaccout(@RequestParam("account") String accout) {
        return userService.getbyaccout(accout);
    }

    //关注
    @ApiOperation(value = "关注他人", notes = "")
    @ApiImplicitParam(name = "userid", value = "用户id", required = true, paramType = "path",dataType = "Intger")
    @RequestMapping(value = "follow.do/{userid}", method = RequestMethod.POST)
    public Msg follow(@PathVariable("userid") int userid, HttpServletRequest request) {
        return userService.addFollow(userid, common.getid(request));
    }

    //获取关注的人
    @ApiOperation(value = "获取关注的人的列表", notes = "")
    @RequestMapping(value = "friends.do", method = RequestMethod.GET)
    public Msg getfriends(HttpServletRequest request) {
        return userService.getFriends(common.getid(request));
    }
}
