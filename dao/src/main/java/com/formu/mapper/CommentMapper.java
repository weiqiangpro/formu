package com.formu.mapper;

import com.formu.bean.Comment;
import com.formu.bean.po.CommentPo;
import io.swagger.models.auth.In;

import java.util.List;

public interface CommentMapper {

    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    CommentPo selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    int updateGoodNumById(Integer commentId, Integer num);

    List<CommentPo> selectByArticleAndisParent(Integer articleId);

    List<CommentPo> selectByParentId(Integer parentId);
}