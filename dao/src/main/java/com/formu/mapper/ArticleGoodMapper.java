package com.formu.mapper;

import com.formu.bean.vo.ArticleGood;

public interface ArticleGoodMapper {
    int deleteByPrimaryKey(Integer agId);

    int insertSelective(ArticleGood record);

    ArticleGood selectByUserAndArticle(Integer userId,Integer articleId);

}