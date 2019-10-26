package com.formu.mapper;

import com.formu.bean.vo.User;

public interface UserMapper {
    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateFollowNumById(Integer userId,Integer num);

    int updateFollowedNumById(Integer userId,Integer num);

    User selectByAccount(String account);

    int updateByAccount(User record);

    User selectByYB(String yb);

}