package com.formu.bean.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Mes {
    private Integer mesId;

    private Integer mesToid;

    private Integer mesFromid;

    private String mesMesage;
}