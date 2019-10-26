package com.formu.Control;

import com.formu.Service.IArticleService;
import com.formu.Utils.FileUtil;
import com.formu.Utils.Msg;
import com.formu.bean.Article;
import com.formu.common.Common;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/article/")
@Slf4j
@CrossOrigin
public class ArticleControl {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private Common common;

    @ApiOperation(value = "获取首页数据", notes = "根据页数获取首页数据,每页10条")
    @ApiImplicitParam(name = "page", value = "page页", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "get/{page}", method = RequestMethod.GET)
    public Msg getAllByPage(@PathVariable("page") int page,HttpServletRequest request) {
        return articleService.getArticleByPage(page, 10,common.getid(request));
    }

    @ApiOperation(value = "通过article的id来获取数据", notes = "获取一条数据")
    @ApiImplicitParam(name = "id", value = "page页", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "getbyid/{id}", method = RequestMethod.GET)
    public Msg getById(@PathVariable("id") int id, HttpServletRequest request) {
        int userid = common.getid(request);
        return articleService.getArticleById(id, userid);
    }

    @ApiOperation(value = "点赞Top", notes = "根据页数获取首页数据,每页10条")
    @ApiImplicitParam(name = "page", value = "page页", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "topgood/{page}", method = RequestMethod.GET)
    public Msg topGood(@PathVariable("page") int page,HttpServletRequest request) {
        return articleService.topByGoodOrCommentOrAll(page, 10,common.getid(request),1);
    }

    @ApiOperation(value = "评论Top", notes = "根据页数获取首页数据,每页10条")
    @ApiImplicitParam(name = "page", value = "page页", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "topcomment/{page}", method = RequestMethod.GET)
    public Msg topGomment(@PathVariable("page") int page, HttpServletRequest request) {
        return articleService.topByGoodOrCommentOrAll(page, 10,common.getid(request),2);
    }

    @ApiOperation(value = "综合Top", notes = "根据页数获取首页数据,每页10条")
    @ApiImplicitParam(name = "page", value = "page页", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "topall/{page}", method = RequestMethod.GET)
    public Msg topAll(@PathVariable("page") int page, HttpServletRequest request) {
        return articleService.topByGoodOrCommentOrAll(page, 10,common.getid(request),3);
    }


    @ApiOperation(value = "获取自己的作品" )
    @ApiImplicitParam(name = "page", value = "page页", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "myarticle.do/{page}", method = RequestMethod.GET)
    public Msg myArticles(@PathVariable("page") int page, HttpServletRequest request) {
        return articleService.getArticleByUserId(page, 10, common.getid(request));
    }

    @ApiOperation(value = "插入数据,需要登录" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "message", value = "文章内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "分类的id，默认为1", dataType = "int"),
            @ApiImplicitParam(name = "height", value = "图片高度", dataType = "int")
    })
    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public Msg insert(@ApiParam(value = "上传的文件", required = true) @RequestParam(value = "file") MultipartFile file,
                      @RequestParam("message") String message,
                      @RequestParam("title") String title,
                      @RequestParam(value = "categoryId", defaultValue = "1") int categoryid,
                      @RequestParam("height") int height,
                      HttpServletRequest request) {

        if (file == null || file.isEmpty()) {
            return Msg.createByErrorMessage("未选择图片");
        }
       String name = FileUtil.save(file);
        if (StringUtils.isBlank(name))
            return Msg.createByErrorMessage("发布文章失败");
         name =name+ "?" + height;
        Article article = new Article();
        article.setTitle(title);
        article.setMessage(message);
        article.setImages(name);
        article.setUserId(common.getid(request));
        article.setCategoryId(categoryid);
        return articleService.insertSelective(article);
    }


    @ApiOperation(value = "更新文章,需要登录" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件",  dataType = "file"),
            @ApiImplicitParam(name = "articleId", value = "文章内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "message", value = "内容",dataType = "int"),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String"),
            @ApiImplicitParam(name = "height", value = "图片高度",  dataType = "int"),
    })
    @RequestMapping(value = "update.do", method = RequestMethod.PUT)
    public Msg update(@RequestParam(value = "file", required = false) MultipartFile file,
                      @RequestParam(value = "articleId",required = true) int articleid,
                      @RequestParam(value = "message",required = false) String message,
                      @RequestParam(value = "title",required = false) String title,
                      @RequestParam(value = "height", required = false) int height,
                      HttpServletRequest request) {
        String name = "";
        Article article = new Article();

        if (file != null && !file.isEmpty()) {
            name = String.valueOf(new Date().getTime()) + new Random(1000).nextInt() + ".png";
            File fie = new File("/log", name);
            try {
                file.transferTo(fie);
            } catch (IOException e) {
                return Msg.createByError();
            }
            name = name + "?" + height;
            article.setImages(name);
        }


        article.setArticleId(articleid);
        article.setMessage(message);
        article.setTitle(title);
        return articleService.updateSelective(article, common.getid(request));
    }

    @ApiOperation(value = "删除文章,需要登录" )
    @ApiImplicitParam(name = "id", value = "文章的id", paramType = "path", dataType = "int")
    @RequestMapping(value = "delete.do/{id}", method = RequestMethod.DELETE)
    public Msg delete(@PathVariable("id") int id, HttpServletRequest request) {
        return articleService.deleteById(id, common.getid(request));
    }

    @ApiOperation(value = "点赞文章,需要登录", notes = "第一次点赞,第二次取消点赞")
    @ApiImplicitParam(name = "id", value = "文章的id", paramType = "path", dataType = "int")
    @RequestMapping(value = "good.do/{id}", method = RequestMethod.PUT)
    public Msg good(@PathVariable("id") int id, HttpServletRequest request) {
        return articleService.goodbyid(id, common.getid(request));
    }


}