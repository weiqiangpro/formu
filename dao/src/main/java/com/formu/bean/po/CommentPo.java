package com.formu.bean.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentPo {
    private Integer commentId;

    private Integer articleId;

    private String message;

    private Integer parentId;

    private Integer fromUSer;

    private String fromName;

    private String fromUSerPho;

    private Integer commentGoodnum;

    private Integer toUser;

    private Integer hour;

    private String toName;
}