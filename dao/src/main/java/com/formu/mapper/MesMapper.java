package com.formu.mapper;

import com.formu.bean.Mes;

public interface MesMapper {
    int deleteByPrimaryKey(Integer mesId);

    int insert(Mes record);

    int insertSelective(Mes record);

    Mes selectByPrimaryKey(Integer mesId);

    int updateByPrimaryKeySelective(Mes record);

    int updateByPrimaryKeyWithBLOBs(Mes record);

    int updateByPrimaryKey(Mes record);
}