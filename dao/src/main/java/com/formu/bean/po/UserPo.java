package com.formu.bean.po;

import com.formu.bean.vo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by weiqiang
 */
@Getter
@Setter
@NoArgsConstructor
public class UserPo {
    private Integer userId;

    private String userName;

    private String pho;

    private String email;

    private String person;

    private boolean isYB;

    private String home;

    private int follownums;

    private int followednums;

    private int sex;

    private String birthday;

    public UserPo(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.pho = user.getPho();
        this.person = user.getPerson();
        this.isYB = StringUtils.isNotBlank(user.getYiban());
        this.email = user.getEmail();
        this.follownums = user.getFollowNum();
        this.followednums = user.getFollowedNum();
        this.home = user.getHome();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (user.getBirthday() != null) {
            String format = dateFormat.format(user.getBirthday());
            this.birthday = format;
        }
        this.sex = user.getSex();
    }
}
