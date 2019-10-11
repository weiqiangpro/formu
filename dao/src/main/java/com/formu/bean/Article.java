package com.formu.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Article {
    private Integer articleId;

    private Integer categoryId;

    private Integer userId;

    private String title;

    private String message;

    private String images;

    private Integer goodNum;

    private Date createTime;

    private String userName;

    private String person;

    private String pho;

    private String categoryName;

    private boolean isgood = false;
}