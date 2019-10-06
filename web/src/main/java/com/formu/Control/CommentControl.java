package com.formu.Control;

import com.formu.Service.ICategoryService;
import com.formu.Service.ICommentService;
import com.formu.Utils.JsonUtil;
import com.formu.Utils.Msg;
import com.formu.bean.Category;
import com.formu.bean.Comment;
import com.formu.bean.User;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/comment/")
public class CommentControl {

    @Autowired
    private ICommentService commentService;

    @RequestMapping(value = "get/{id}/{pagenum}", method = RequestMethod.GET)
    public Msg getall(@PathVariable("id") int id, @PathVariable("pagenum") int pagenum) {
        return commentService.getCommentyArticleAndisParent(pagenum, 10, id);
    }


    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public Msg insert(Comment comment, HttpServletRequest request) {
        String token = request.getHeader("Token");
        User user = JsonUtil.string2Obj(token, User.class);
        if (user != null) {
            comment.setFormUser(user.getUserId());
            if (comment.getParentId() == null || comment.getToUser() == null) {
                return commentService.insertIsParent(comment);
            }
            return commentService.insertNotParent(comment);
        }
        return Msg.createByError();
    }

    @RequestMapping(value = "delete.do/{id}",method = RequestMethod.DELETE)
    public Msg delete(@PathVariable("id")int id){
        return commentService.deleteById(id);
    }

}