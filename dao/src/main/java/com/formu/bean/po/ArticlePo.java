package com.formu.bean.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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