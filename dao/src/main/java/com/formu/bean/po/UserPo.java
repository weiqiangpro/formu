package com.formu.bean.po;

import com.formu.bean.User;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 * Created by weiqiang
 */
@Getter
public class UserPo {
    private Integer userId;

    private String userName;

    private String pho;

    private String email;

    public UserPo(User user, boolean isme) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.pho = user.getPho();
        if (isme)
            this.email = user.getEmail();
        else
            this.email = StringUtils.substring(user.getEmail(), 0, 5) + "*******@" + StringUtils.substringAfter(user.getEmail(), "@");
    }
}
