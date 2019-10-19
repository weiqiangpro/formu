package com.formu.mapper;

import com.formu.bean.CommentGood;

public interface CommentGoodMapper {
    int deleteByPrimaryKey(Integer cgId);

    int insert(CommentGood record);

    int insertSelective(CommentGood record);

    CommentGood selectByPrimaryKey(Integer cgId);

    int updateByPrimaryKeySelective(CommentGood record);

    int updateByPrimaryKey(CommentGood record);

    CommentGood selectByUserAndComment(int userId, int commentId);
}