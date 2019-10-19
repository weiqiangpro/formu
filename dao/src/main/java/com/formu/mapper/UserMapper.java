package com.formu.mapper;

import com.formu.bean.User;
import com.formu.bean.po.FollowInfo;
import io.swagger.models.auth.In;

import java.util.List;
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateFollowNumById(Integer userId,Integer num);

    User selectByAccount(String account);

    int updateByAccount(User record);

    User selectByYB(String yb);


}