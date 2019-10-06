package com.formu.Control;

import com.formu.Service.imp.UserService;
import com.formu.Utils.Md5Utils;
import com.formu.Utils.Msg;
import com.formu.bean.User;
import com.formu.common.Common;
import org.apache.commons.lang.StringUtils;
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

    @RequestMapping("other/{id}")
    public Msg getother(@PathVariable("id") int userid) {
        return userService.getOtherById(userid);
    }

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

    @RequestMapping(value = "email", method = RequestMethod.POST)
    public Msg send(@RequestParam("email") String email,
                    @RequestParam("account") String account) {
        Long expire = redis.getExpire(account, TimeUnit.SECONDS);
        if (expire > 120) {
            return Msg.createByErrorMessage("请60s后再发送邮件");
        }
        if (common.sendEmail(email, account)) {
            return Msg.createBySuccessMessage("邮件发送成功");
        }
        return Msg.createByErrorMessage("邮件发送失败");
    }

}
