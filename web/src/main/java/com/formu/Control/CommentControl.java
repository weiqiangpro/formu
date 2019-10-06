package com.formu.Control;

import com.formu.Service.ICommentService;
import com.formu.Utils.Msg;
import com.formu.bean.Comment;
import com.formu.common.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/comment/")
public class CommentControl {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private Common common;

    @RequestMapping(value = "get/{id}/{pagenum}", method = RequestMethod.GET)
    public Msg getall(@PathVariable("id") int id, @PathVariable("pagenum") int pagenum) {
        return commentService.getCommentyArticleAndisParent(pagenum, 10, id);
    }


    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public Msg insert(@RequestParam("articleId") int articleid,
                      @RequestParam("message") String message,
                      @RequestParam(value = "parentId", defaultValue = "0") int parentid,
                      @RequestParam(value = "touser", defaultValue = "0") int toid,
                      HttpServletRequest request) {

        Comment comment = new Comment();
        comment.setArticleId(articleid);
        comment.setMessage(message);
        comment.setParentId(parentid);
        comment.setFromUSer(common.getid(request));
        comment.setToUser(toid);
        if (parentid == 0 || toid == 0) {
            return commentService.insertIsParent(comment);
        }
        return commentService.insertNotParent(comment);
    }

    @RequestMapping(value = "delete.do/{id}", method = RequestMethod.DELETE)
    public Msg delete(@PathVariable("id") int id,HttpServletRequest request) {
        return commentService.deleteById(id,common.getid(request));
    }

}