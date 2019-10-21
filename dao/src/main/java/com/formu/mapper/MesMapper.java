package com.formu.mapper;

import com.formu.bean.Mes;
import com.formu.bean.po.FollowInfo;
import com.formu.bean.po.MesInfo;

import java.util.List;

public interface MesMapper {
    int deleteByPrimaryKey(Integer mesId);

    int insertSelective(Mes record);

    Mes selectByPrimaryKey(Integer mesId);

    MesInfo selectByPrimaryKey2(Integer mesId);

    int updateByPrimaryKeySelective(Mes record);

    List<FollowInfo> getMessages(int userid);
}