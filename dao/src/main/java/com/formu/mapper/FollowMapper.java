package com.formu.mapper;

import com.formu.bean.Follow;
import com.formu.bean.po.FollowInfo;

import java.util.List;

public interface FollowMapper {
    int deleteByPrimaryKey(Integer followId);

    int insert(Follow record);

    int insertSelective(Follow record);

    Follow selectByPrimaryKey(Integer followId);

    int updateByPrimaryKeySelective(Follow record);

    int updateByPrimaryKey(Follow record);

    Follow selectByMeAndOther(int userId, int otherId);

    List<FollowInfo> getFrendsByUserId(Integer userId);

}