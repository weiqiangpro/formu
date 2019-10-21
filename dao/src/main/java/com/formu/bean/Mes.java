package com.formu.bean;

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

    private Integer mesFromid;

    private Date mesCreatetime;

    private Integer mesIsread;
}