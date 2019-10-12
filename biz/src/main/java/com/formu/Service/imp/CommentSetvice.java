package com.formu.Service.imp;

import com.formu.Service.ICommentService;
import com.formu.Utils.Msg;
import com.formu.Utils.SetUtil;
import com.formu.bean.Comment;
import com.formu.bean.User;
import com.formu.bean.po.CommentPo;
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
    private UserMapper userMapper;

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

        CommentPo comment = commentMapper.selectByPrimaryKey(id);
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

    @Override
    public Msg goodbyid(int id, int userid) {
        if (commentMapper.selectByPrimaryKey(id)==null)
            return Msg.createBySuccessMessage("该评论不存在！");

        User user = userMapper.selectByPrimaryKey(userid);
        String good1 = userMapper.selectByPrimaryKey(userid).getCommentGood();
        String good2 = SetUtil.upGood(good1, id);

        int n1 = good1 == null ? 0:good1.length();
        int n2 = good2 == null ? 0:good2.length();
        if (n1 == n2)
            return Msg.createByErrorMessage("点赞失败");
        user.setCommentGood(good2);


        int ok2 = userMapper.updateByPrimaryKeySelective(user);

        int ok1 = commentMapper.updateGoodNumById(id, n2 > n1 ? 1 : -1);
        if (ok1 > 0 && ok2 > 0) {
            if (n2 > n1)
                return Msg.createByErrorMessage("点赞成功！");
            else
                return Msg.createBySuccessMessage("取消点赞成功！");
        }

        return Msg.createBySuccessMessage("点赞失败！");

    }
}
