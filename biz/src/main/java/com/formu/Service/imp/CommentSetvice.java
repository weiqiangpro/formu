package com.formu.Service.imp;

import com.formu.Service.ICommentService;
import com.formu.Utils.Msg;
import com.formu.bean.Comment;
import com.formu.bean.CommentGood;
import com.formu.bean.po.CommentPo;
import com.formu.mapper.ArticleMapper;
import com.formu.mapper.CommentGoodMapper;
import com.formu.mapper.CommentMapper;
import com.formu.mapper.UserMapper;
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

    @Autowired
    private ArticleMapper  articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentGoodMapper commentGoodMapper;

    @Override
    public Msg getCommentyArticleAndisParent(int pageNum, int pageSize, int id) {
        PageHelper.startPage(pageNum, pageSize);

        List<CommentPo> commentList = commentMapper.selectByArticleAndisParent(id);
        List<CommentPo> newlist = new ArrayList<>();
        getList(commentList,newlist);
        PageInfo<CommentPo> pageResult = new PageInfo<>(newlist);
        return Msg.createBySuccess(pageResult);
    }

    private void getList(List<CommentPo> list, List<CommentPo> newlist){
        for (CommentPo comment : list) {
            newlist.add(comment);
            List<CommentPo> list1 = commentMapper.selectByParentId(comment.getCommentId());
            newlist.addAll(list1);
        }
    }


    @Override
    public Msg insertNotParent(Comment comment) {
        if (comment.getParentId()==0 || comment.getToUser()==0)
            return Msg.createByErrorMessage("添加评论失败!");

        CommentPo comment1 = commentMapper.selectByPrimaryKey(comment.getParentId());

        if (comment1==null || comment1.getParentId()!=0)
        return Msg.createByErrorMessage("添加评论失败!");

            int ok1 = commentMapper.insertSelective(comment);
            int ok2 = articleMapper.updateCommnetNumById(comment.getArticleId(),1);
            if (ok1 > 0 && ok2 > 0) {
                return Msg.createBySuccessMessage("添加评论成功!");
            }
            return Msg.createByErrorMessage("添加评论失败!");

    }

    @Override
    public Msg insertIsParent(Comment comment) {
        if (comment!=null) {
            int ok1 = commentMapper.insertSelective(comment);
            int ok2 = articleMapper.updateCommnetNumById(comment.getArticleId(),1);
            if (ok1 > 0 && ok2 > 0) {
                return Msg.createBySuccessMessage("添加评论成功!");
            }
            return Msg.createByErrorMessage("添加评论失败!");
        }
        return Msg.createByErrorMessage("添加评论失败!");
    }

    @Override
    public Msg deleteById(int id,int userid) {

        CommentPo comment = commentMapper.selectByPrimaryKey(id);
        if (comment == null)
            return Msg.createByErrorMessage("不存在该评论！");

        if (userid != comment.getFromUSer())
            return Msg.createByErrorMessage("删除评论失败!您不是本评论的作者，无权删除该评论!");

        int ok1 = commentMapper.deleteByPrimaryKey(id);
        int ok2 = articleMapper.updateCommnetNumById(comment.getArticleId(),-1);
        if (ok1 > 0 && ok2 > 0) {
            return Msg.createBySuccessMessage("删除评论成功!");
        }
        return Msg.createByErrorMessage("删除评论失败!");
    }

    @Override
    public Msg goodbyid(int commentId, int userId) {
        if (commentMapper.selectByPrimaryKey(commentId)==null)
            return Msg.createBySuccessMessage("该评论不存在！");

        CommentGood commentGood = commentGoodMapper.selectByUserAndComment(userId, commentId);
        if (commentGood == null) {
            int ok1 = commentGoodMapper.insertSelective(new CommentGood(null, commentId, userId));
            int ok2 = commentMapper.updateGoodNumById(commentId, 1);
            if (ok1 > 0 && ok2 > 0)
                return Msg.createBySuccessMessage("点赞成功！");
            else
                return Msg.createBySuccessMessage("点赞失败！");
        } else {
            int ok3 = commentGoodMapper.deleteByPrimaryKey(commentGood.getCgId());
            int ok4 = articleMapper.updateGoodNumById(commentId, -1);
            if (ok3 > 0 && ok4 > 0)
                return Msg.createBySuccessMessage("取消点赞成功！");
            else
                return Msg.createBySuccessMessage("点赞失败！");
        }
    }
}
