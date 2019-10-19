package com.formu.bean.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by weiqiang
 */
@Getter
@Setter
@NoArgsConstructor
public class MesInfo {
    private int mesId;
    private String message;
    private int userId;
    private String name;
    private String pho;
    private int isread;
}
