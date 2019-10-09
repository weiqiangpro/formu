package com.formu.Control;

import com.formu.Service.IArticleService;
import com.formu.Utils.Msg;
import com.formu.bean.Article;
import com.formu.common.Common;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    @ApiImplicitParam(name = "page", value = "page页", required = true, paramType = "path", dataType = "Integer")
    @RequestMapping(value = "get/{page}", method = RequestMethod.GET)
    public Msg getall(@PathVariable("page") int page) {
        return articleService.getArticleByPage(page, 10);
    }

    @ApiOperation(value = "通过article的id来获取数据", notes = "获取一条数据")
    @ApiImplicitParam(name = "page", value = "page页", required = true, paramType = "path", dataType = "Integer")
    @RequestMapping(value = "getbyid/{id}", method = RequestMethod.GET)
    public Msg getById(@PathVariable("id") int id, HttpServletRequest request) {
        int userid = common.getid(request);
        return articleService.getArticleById(id, userid);
    }

    @ApiOperation(value = "根据分类获取数据", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryid", value = "分类的id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "page页", required = true, paramType = "path", dataType = "Integer")
    })
    @RequestMapping(value = "getbycategory/{categoryid}/{page}", method = RequestMethod.GET)
    public Msg getByCategory(@PathVariable("categoryid") int categoryid, @PathVariable("page") int page) {
        return articleService.getArticleByCategory(page, 10, categoryid);
    }


    @ApiOperation(value = "插入数据,需要登录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件", required = true, dataType = "file"),
            @ApiImplicitParam(name = "message", value = "文章内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "categoryId", value = "分类的id，默认为1", dataType = "Integer"),
            @ApiImplicitParam(name = "height", value = "图片高度", dataType = "Integer")
    })
    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public Msg insert(@RequestParam(value = "file") MultipartFile file,
                      @RequestParam("message") String message,
                      @RequestParam("title") String title,
                      @RequestParam(value = "categoryId", defaultValue = "1") int categoryid,
                      @RequestParam("height") int height,
                      HttpServletRequest request) {

        if (file == null || file.isEmpty()) {
            return Msg.createByErrorMessage("未选择图片");
        }
        String name = String.valueOf(new Date().getTime()) + new Random(1000).nextInt() + ".png";
        File fie = new File("/log", name);
        try {
            file.transferTo(fie);
        } catch (IOException e) {
            return Msg.createByError();
        }
        name = name + "?" + height;
        Article article = new Article();
        article.setTitle(title);
        article.setMessage(message);
        article.setImages(name);
        article.setUserId(common.getid(request));
        article.setGoodNum(0);
        article.setCategoryId(categoryid);
        return articleService.insertSelective(article);
    }


    @ApiOperation(value = "更新文章,需要登录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件", required = false, dataType = "file"),
            @ApiImplicitParam(name = "articleId", value = "文章内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "message", value = "内容", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "height", value = "图片高度", required = false, dataType = "Integer"),
    })
    @RequestMapping(value = "update.do", method = RequestMethod.PUT)
    public Msg update(@RequestParam(value = "file", required = false) MultipartFile file,
                      @RequestParam("articleId") int articleid,
                      @RequestParam("message") String message,
                      @RequestParam("title") String title,
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

    @ApiOperation(value = "删除文章,需要登录", notes = "")
    @ApiImplicitParam(name = "id", value = "文章的id", paramType = "path", dataType = "Integer")
    @RequestMapping(value = "delete.do/{id}", method = RequestMethod.DELETE)
    public Msg delete(@PathVariable("id") int id, HttpServletRequest request) {
        return articleService.deleteById(id, common.getid(request));
    }

    @ApiOperation(value = "点赞文章,需要登录", notes = "第一次点赞,第二次取消点赞")
    @ApiImplicitParam(name = "id", value = "文章的id", paramType = "path", dataType = "Integer")
    @RequestMapping(value = "good.do/{id}", method = RequestMethod.PUT)
    public Msg update(@PathVariable("id") int id, HttpServletRequest request) {
        return articleService.goodbyid(id, common.getid(request));
    }


}