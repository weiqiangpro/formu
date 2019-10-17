package com.formu.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by weiqiang
 */
@Getter
@Setter
@NoArgsConstructor
public class Token {
    private String access_token;
    private  String userid;
    private  String expires;
}
