package com.formu.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Comment {
    private Integer commentId;

    private Integer articleId;

    private String message;

    private Integer parentId;

    private Integer fromUSer;

    private String fromName;


    private Integer toUser;

    private String toName;

    private Date createTime;


}