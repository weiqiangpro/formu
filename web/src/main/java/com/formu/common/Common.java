package com.formu.common;

import com.formu.Utils.JsonUtil;
import com.formu.Utils.Msg;
import com.formu.bean.User;
import com.formu.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by weiqiang
 */
@Component
@Slf4j
public class Common {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redis;



    public boolean registerEmail(String email,String account) {
        try {
            User user = userMapper.selectByAccount(account);
            if (user != null)
                return false;
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//            message.setFrom("weinichix@qq.com");
//            message.setTo(email);
//            String str = "您的验证码为"+getCode(4);
//            message.setSubject("图片论坛验证码!");
//            message.setText(str);
//            mailSender.send(mimeMessage);
            String code = getCode();
            redis.opsForValue().set(account,code,3, TimeUnit.MINUTES);
            log.info(account);
            log.info("注册，验证码为______ {} _______",code);
            log.info( redis.opsForValue().get(account));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean findEmail(String account) {
        try {
            User user = userMapper.selectByAccount(account);
            if (user == null)
                return false;
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//            message.setFrom("weinichix@qq.com");
//            message.setTo(user.getEmail());
//            String str = "您的验证码为"+getCode(4);
//            message.setSubject("图片论坛验证码!");
//            message.setText(str);
//            mailSender.send(mimeMessage);
            String code = getCode();
            redis.opsForValue().set(account,code,3, TimeUnit.MINUTES);
            log.info(account);
            log.info("找回密码，验证码为______ {} _______",code);
            log.info( redis.opsForValue().get(account));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    private  String getCode() {
        String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] ch = new char[4];
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(string.length());
            ch[i] = string.charAt(index);//charAt() : 返回指定索引处的 char 值   ==》保存到字符数组对象ch里面
        }
        return String.valueOf(ch);
    }



    public  int getid(HttpServletRequest request) {
        String header = request.getHeader("Token");
        if (StringUtils.isBlank(header)) {
            return 0;
        }
        String token = redis.opsForValue().get(header);
        User user = JsonUtil.string2Obj(token, User.class);
        if (user == null) {
            return 0;
        }
        return user.getUserId();
    }
    public  User getUser(HttpServletRequest request) {
        String header = request.getHeader("Token");
        if (StringUtils.isBlank(header)) {
            return null;
        }
        String token = redis.opsForValue().get(header);
        return JsonUtil.string2Obj(token, User.class);
    }

    public   String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }
}
