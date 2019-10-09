package com.formu.Service.imp;

import com.formu.Service.ICommentService;
import com.formu.Utils.Msg;
import com.formu.bean.Article;
import com.formu.bean.Comment;
import com.formu.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiqiang
 */
@Service
public class CommentSetvice implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Msg getCommentyArticleAndisParent(int pageNum, int pageSize, int id) {
        PageHelper.startPage(pageNum, pageSize);

        List<Comment> commentList = commentMapper.selectByArticleAndisParent(id);
        List<Comment> newlist = new ArrayList<>();
        getList(commentList,newlist);
        PageInfo<Comment> pageResult = new PageInfo<>(newlist);
        return Msg.createBySuccess(pageResult);
    }

    private void getList(List<Comment> list,List<Comment> newlist){
        for (Comment comment : list) {
            newlist.add(comment);
            List<Comment> list1 = commentMapper.selectByParentId(comment.getCommentId());
            newlist.addAll(list1);
        }
    }


    @Override
    public Msg insertNotParent(Comment comment) {
        if (comment.getParentId()==0 || comment.getToUser()==0)
            return Msg.createByErrorMessage("添加评论失败!");

        Comment comment1 = commentMapper.selectByPrimaryKey(comment.getParentId());

        if (comment1==null || comment1.getParentId()!=0)
        return Msg.createByErrorMessage("添加评论失败!");

            int ok = commentMapper.insertSelective(comment);
            if (ok > 0) {
                return Msg.createBySuccessMessage("添加评论成功!");
            }
            return Msg.createByErrorMessage("添加评论失败!");

    }

    @Override
    public Msg insertIsParent(Comment comment) {
        if (comment!=null) {
            int ok = commentMapper.insertSelective(comment);
            if (ok > 0) {
                return Msg.createBySuccessMessage("添加评论成功!");
            }
            return Msg.createByErrorMessage("添加评论失败!");
        }
        return Msg.createByErrorMessage("添加评论失败!");
    }

    @Override
    public Msg deleteById(int id,int userid) {

        Comment comment = commentMapper.selectByPrimaryKey(id);
        if (comment == null)
            return Msg.createByErrorMessage("不存在该评论！");

        if (userid != comment.getFromUSer())
            return Msg.createByErrorMessage("删除评论失败!您不是本评论的作者，无权删除该评论!");

        int ok = commentMapper.deleteByPrimaryKey(id);
        if (ok > 0) {
            return Msg.createBySuccessMessage("删除评论成功!");
        }
        return Msg.createByErrorMessage("删除评论失败!");
    }
}
