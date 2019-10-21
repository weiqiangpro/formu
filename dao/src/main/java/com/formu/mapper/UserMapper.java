package com.formu.mapper;

import com.formu.bean.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateFollowNumById(Integer userId,Integer num);

    int updateFollowedNumById(Integer userId,Integer num);

    User selectByAccount(String account);

    int updateByAccount(User record);

    User selectByYB(String yb);


}