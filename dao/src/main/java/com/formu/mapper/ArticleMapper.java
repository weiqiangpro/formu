package com.formu.mapper;

import com.formu.bean.po.ArticleTop;
import com.formu.bean.vo.Article;
import com.formu.bean.po.ArticlePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {

    int deleteByPrimaryKey(Integer articleId);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateGoodNumById(Integer articleId,Integer num);

    int updateCommnetNumById(Integer articleId,Integer num);

    List<ArticlePo> selectall();

    List<ArticlePo> select(String mes);

    List<ArticlePo> selectByUserId(Integer userId);

    List<Article> selectByUserIds(@Param("userIdList")List<Integer> userIdList);

    List<ArticleTop>  selectByGoodAndComment();

    List<ArticleTop>  selectByGood();

    List<ArticleTop>  selectByComment();
}