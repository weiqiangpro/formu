package com.formu.mapper;

import com.formu.bean.Follow;
import com.formu.bean.po.FollowInfo;

import java.util.List;

public interface FollowMapper {
    int deleteByPrimaryKey(Integer followId);


    int insertSelective(Follow record);


    Follow selectByMeAndOther(int userId, int otherId);

    List<FollowInfo> getFollowsByUserId(Integer userId);

    List<FollowInfo> getFollowedsByUserId(int userid);
}