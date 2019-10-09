package com.formu.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Category {
    private Integer categoryId;

    private String categoryName;

    private Integer parentId;


}