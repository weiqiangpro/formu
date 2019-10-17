package com.formu.mapper;

import com.formu.bean.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    User selectOtherByPrimaryKey(Integer userId);

    User selectByAccount(String account);

    User selectByYB(String yb);

    User selectLoginByAccount(String account);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateByAccount(User record);
}