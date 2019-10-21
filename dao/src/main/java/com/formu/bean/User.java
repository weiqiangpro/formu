package com.formu.bean;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}