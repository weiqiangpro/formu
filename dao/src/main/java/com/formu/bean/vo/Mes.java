package com.formu.bean.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Mes {
    private Integer mesId;

    private String mesMessage;

    private Integer mesToid;

    private Integer mesTo;

    private Integer mesFromid;

    private Integer mesFrom;

    private Date mesCreatetime;

    private Integer mesIsread;
}