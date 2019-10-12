package com.formu.Control;

import com.formu.Service.ICommentService;
import com.formu.Utils.Msg;
import com.formu.bean.Comment;
import com.formu.common.Common;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/comment/")
@CrossOrigin
public class CommentControl {

    @Autowired
    private ICommentService commentService;

    @Autowired


    private Common common;

    @ApiOperation(value = "根据文章的id获取评论内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "pagenum", value = "page页，每页10条", required = true, paramType = "path", dataType = "Integer")
    })
    @RequestMapping(value = "get/{id}/{pagenum}", method = RequestMethod.GET)
    public Msg getall(@PathVariable("id") int id, @PathVariable("pagenum") int pagenum) {
        return commentService.getCommentyArticleAndisParent(pagenum, 10, id);
    }

    @ApiOperation(value = "添加评论,需要登录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "message", value = "内容", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "parentId", value = "如果是回复就返回父评论的id，默认为0", dataType = "String"),
            @ApiImplicitParam(name = "touser", value = "如果是回复就是回复那个人的用户id，默认为0", dataType = "Integer")
    })
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
        comment.setCommentGoodnum(0);
        if (parentid == 0 || toid == 0) {
            return commentService.insertIsParent(comment);
        }
        return commentService.insertNotParent(comment);
    }

    @ApiOperation(value = "根据评论的id删除,需要登录", notes = "")
    @ApiImplicitParam(name = "id", value = "评论id", required = true, paramType = "path", dataType = "Integer")
    @RequestMapping(value = "delete.do/{id}", method = RequestMethod.DELETE)
    public Msg delete(@PathVariable("id") int id, HttpServletRequest request) {
        return commentService.deleteById(id, common.getid(request));
    }

    @ApiOperation(value = "点赞评论,需要登录", notes = "第一次点赞,第二次取消点赞")
    @ApiImplicitParam(name = "id", value = "评论的id", paramType = "path", dataType = "Integer")
    @RequestMapping(value = "good.do/{id}", method = RequestMethod.PUT)
    public Msg update(@PathVariable("id") int id, HttpServletRequest request) {
        return commentService.goodbyid(id, common.getid(request));
    }

}