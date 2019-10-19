package com.formu.mapper;

import com.formu.bean.ArticleGood;

public interface ArticleGoodMapper {
    int deleteByPrimaryKey(Integer agId);

    int insert(ArticleGood record);

    int insertSelective(ArticleGood record);

    ArticleGood selectByPrimaryKey(Integer agId);

    ArticleGood selectByUserAndArticle(Integer userId,Integer articleId);

    int updateByPrimaryKeySelective(ArticleGood record);

    int updateByPrimaryKey(ArticleGood record);
}