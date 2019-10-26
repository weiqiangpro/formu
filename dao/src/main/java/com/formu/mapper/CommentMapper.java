package com.formu.mapper;

import com.formu.bean.vo.Comment;
import com.formu.bean.po.CommentPo;

import java.util.List;

public interface CommentMapper {

    int deleteByPrimaryKey(Integer commentId);

    int insertSelective(Comment record);

    CommentPo selectByPrimaryKey(Integer commentId);

    int updateGoodNumById(Integer commentId, Integer num);

    List<CommentPo> selectByArticleAndisParent(Integer articleId);

    List<CommentPo> selectByParentId(Integer parentId);
}