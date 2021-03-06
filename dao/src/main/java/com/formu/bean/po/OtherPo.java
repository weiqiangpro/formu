package com.formu.bean.po;

import com.formu.bean.vo.User;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by weiqiang
 */
@Getter
public class OtherPo {
    private Integer userId;

    private String userName;

    private String pho;

    private String email;

    private String person;

    private int follownums;

    private int followednums;

    private boolean isFollow;

    private String home;

    private int sex;

    private String birthday;

    public OtherPo(User user, boolean isFollow) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.pho = user.getPho();
        this.person = user.getPerson();
        this.email = StringUtils.substring(user.getEmail(), 0, 5) + "*******@" + StringUtils.substringAfter(user.getEmail(), "@");
        this.follownums = user.getFollowNum();
        this.followednums = user.getFollowedNum();
        this.isFollow = isFollow;
        this.home = user.getHome();
        if (user.getBirthday() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            this.birthday = dateFormat.format(user.getBirthday());
        }
        this.sex = user.getSex();
    }
}
