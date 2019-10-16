package com.formu.bean.po;

import com.formu.bean.Category;
import com.formu.bean.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ArticlePo {
    private Integer articleId;

    private String articleImages;

    private Integer goodNum;

    private Integer hour;

    private Integer commentNum;
}