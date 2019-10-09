package com.formu.mapper;

import com.formu.bean.Comment;
import java.util.List;

public interface CommentMapper {

    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByArticleAndisParent(Integer articleId);

    List<Comment> selectByParentId(Integer parentId);
}