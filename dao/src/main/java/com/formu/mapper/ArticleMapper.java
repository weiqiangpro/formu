package com.formu.mapper;

import com.formu.bean.Article;

import java.util.List;

public interface ArticleMapper {

    int deleteByPrimaryKey(Integer articleId);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    int updateGoodNumById(Integer articleId,Integer num);

    int updateCommnetNumById(Integer articleId,Integer num);

    List<Article> selectall();

    List<Article> selectByCategory(Integer id);
}