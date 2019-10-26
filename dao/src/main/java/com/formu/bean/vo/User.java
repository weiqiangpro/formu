package com.formu.bean.vo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Integer userId;

    private String userName;

    private String passwd;

    private String pho;

    private String email;

    private String yiban;

    private String account;

    private String person;

    private Integer followNum;

    private Integer followedNum;

    private String home;

    private Date birthday;

    private Integer sex;
}