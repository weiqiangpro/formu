package com.formu.Control;

import com.formu.Service.imp.UserService;
import com.formu.Utils.FileUtil;
import com.formu.Utils.Md5Utils;
import com.formu.Utils.Msg;
import com.formu.bean.User;
import com.formu.common.Common;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @ApiOperation(value = "获取别人的信息")
    @ApiImplicitParam(name = "id", value = "用户的id", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "other/{id}", method = RequestMethod.GET)
    public Msg getother(@PathVariable("id") int otherId, HttpServletRequest request) {
        return userService.getOtherById(otherId, common.getid(request));
    }

    @ApiOperation(value = "获取自己的信息")
    @RequestMapping(value = "me.do", method = RequestMethod.GET)
    public Msg getme(HttpServletRequest request) {
        return userService.getMyByid(common.getid(request));
    }


    @ApiOperation(value = "检查该账号是否已存在")
    @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String")
    @RequestMapping(value = "isregister", method = RequestMethod.GET)
    public Msg isregister(@RequestParam(value = "account", required = true) String accout) {
        return userService.isregister(accout);
    }

    @ApiOperation(value = "注册")
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

    @ApiOperation(value = "注册时使用的发送邮箱验证码,根据账号判断,每间隔60秒才能发送")
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


    @ApiOperation(value = "修改密码,需要登录")
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
    @ApiOperation(value = "找回密码")
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

    @ApiOperation(value = "找回密码时使用的发送邮箱验证码,根据账号判断,每间隔60秒才能发送")
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
    @ApiOperation(value = "获取邮箱,带*******的邮箱,再找回密码的时候使用")
    @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String")
    @RequestMapping(value = "getemail", method = RequestMethod.GET)
    public Msg getbyaccout(@RequestParam("account") String accout) {
        return userService.getbyaccout(accout);
    }

    //关注
    @ApiOperation(value = "关注他人")
    @ApiImplicitParam(name = "userid", value = "用户id", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "follow.do/{userid}", method = RequestMethod.POST)
    public Msg follow(@PathVariable("userid") int userid, HttpServletRequest request) {
        return userService.addFollow(userid, common.getid(request));
    }

    //获取关注的人
    @ApiOperation(value = "获取我关注人的列表")
    @RequestMapping(value = "myfollows.do", method = RequestMethod.GET)
    public Msg getFollows(HttpServletRequest request) {
        return userService.getFollows(common.getid(request));
    }

    @ApiOperation(value = "获取关注我的人的列表")
    @RequestMapping(value = "myfolloweds.do", method = RequestMethod.GET)
    public Msg getFolloweds(HttpServletRequest request) {
        return userService.getFolloweds(common.getid(request));
    }


    //获取关注的人
    @ApiOperation(value = "获取其他用户关注人的列表")
    @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "follows/{userid}", method = RequestMethod.GET)
    public Msg getFollowsByUserid(@PathVariable("userid") int userid) {
        return userService.getFollows(userid);
    }

    @ApiOperation(value = "获取关注其他用户的人的列表")
    @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "followeds/{userid}", method = RequestMethod.GET)
    public Msg getFollowedsByUserid(@PathVariable("userid") int userid) {
        return userService.getFolloweds(userid);
    }

    @ApiOperation(value = "修改个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "昵称", dataType = "String"),
            @ApiImplicitParam(name = "person", value = "个人简介", dataType = "String"),
            @ApiImplicitParam(name = "home", value = "家乡简介", dataType = "String"),
            @ApiImplicitParam(name = "birthday", value = "出生日", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别,0为男,1为女", dataType = "int"),
    })
    @RequestMapping(value = "information.do", method = RequestMethod.POST)
    public Msg modify(@ApiParam(value = "上传的文件", required = false) @RequestParam(value = "file", required = false) MultipartFile file,
                      @RequestParam(value = "username", required = false) String name,
                      @RequestParam(value = "person", required = false) String person,
                      @RequestParam(value = "home", required = false) String home,
                      @RequestParam(value = "birthday", required = false) String birthday,
                      @RequestParam(value = "sex", required = false, defaultValue = "3") int sex, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        user.setUserId(common.getid(request));
        user.setUserName(name);
        user.setPerson(person);
        user.setHome(home);
        if (birthday != null) {
            try {
                Date date =  sdf.parse(birthday);
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(date);
                cal1.set(Calendar.DAY_OF_MONTH,cal1.get(Calendar.DAY_OF_MONTH)+1);
                user.setBirthday(cal1.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (sex != 3)
            user.setSex(sex);
        if (file != null && !file.isEmpty()) {
            String header = FileUtil.save(file);
            if (StringUtils.isBlank(header))
                return Msg.createByErrorMessage("发布文章失败");
            user.setPho(header);
        }
        return userService.modifyInformation(user);
    }

}
