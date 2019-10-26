package com.formu.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    private Integer  followId;

    private Integer followToid;

    private Integer followFromid;

}