package com.formu.bean.po;

import com.formu.bean.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

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
    }
}
