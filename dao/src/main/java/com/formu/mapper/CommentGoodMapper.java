package com.formu.mapper;

import com.formu.bean.CommentGood;

public interface CommentGoodMapper {
    int deleteByPrimaryKey(Integer cgId);

    int insertSelective(CommentGood record);

    CommentGood selectByUserAndComment(int userId, int commentId);
}