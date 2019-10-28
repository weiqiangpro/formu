package com.formu.bean.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleTop {
    private Integer articleId;

    private String articleImages;

    private Integer goodNum;

    private String hour;

    public void setHour(Integer hour) {
        if (hour < 60)
            this.hour = hour + "秒之前";
        else if (hour < 3600)
            this.hour = hour / 60 + "分钟之前";
        else if (hour < 3600 * 24)
            this.hour = hour / 3600 + "小时之前";
        else if (hour < 3600 * 24 * 30)
            this.hour = hour / (3600 * 24) + "天之前";
        else if (hour < 3600 * 24 * 30 * 12)
            this.hour = hour / (3600 * 24 * 30) + "月之前";
        else
            this.hour = hour / (3600 * 24 * 30 * 24) + "年之前";
    }

    private Integer commentNum;

    private boolean isgood;

    private String name;

    private String userid;

    private String pho;
}