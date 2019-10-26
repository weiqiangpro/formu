package com.formu.mapper;

import com.formu.bean.vo.Article;
import com.formu.bean.po.ArticlePo;

import java.util.List;

public interface ArticleMapper {

    int deleteByPrimaryKey(Integer articleId);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateGoodNumById(Integer articleId,Integer num);

    int updateCommnetNumById(Integer articleId,Integer num);

    List<ArticlePo> selectall();

    List<ArticlePo> selectByUserId(Integer userId);

    List<ArticlePo>  selectByGoodAndComment();

    List<ArticlePo>  selectByGood();

    List<ArticlePo>  selectByComment();
}