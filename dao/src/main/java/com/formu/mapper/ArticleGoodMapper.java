package com.formu.mapper;

import com.formu.bean.ArticleGood;
import com.formu.bean.po.ArticlePo;
import java.util.List;

public interface ArticleGoodMapper {
    int deleteByPrimaryKey(Integer agId);

    int insertSelective(ArticleGood record);

    ArticleGood selectByUserAndArticle(Integer userId,Integer articleId);

}