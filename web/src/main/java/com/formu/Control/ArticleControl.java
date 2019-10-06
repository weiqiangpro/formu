package com.formu.Control;

import com.formu.Service.IArticleService;
import com.formu.Utils.Msg;
import com.formu.bean.Article;
import com.formu.common.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/article/")
@Slf4j
public class ArticleControl {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private Common common;

    @RequestMapping(value = "get/{page}", method = RequestMethod.GET)
    public Msg getall(@PathVariable("page") int page) {
        return articleService.getArticleByPage(page, 10);
    }

    @RequestMapping(value = "getbyid/{id}", method = RequestMethod.GET)
    public Msg getById(@PathVariable("id") int id, HttpServletRequest request) {
        int userid = common.getid(request);
        return articleService.getArticleById(id, userid);
    }

    @RequestMapping(value = "getbycategory/{categoryid}/{page}", method = RequestMethod.GET)
    public Msg getByCategory(@PathVariable("categoryid") int categoryid, @PathVariable("page") int page) {
        return articleService.getArticleByCategory(page, 10, categoryid);
    }

    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public Msg insert(@RequestParam("message") String message,
                      @RequestParam("title") String title,
                      @RequestParam("photo") String photo,
                      @RequestParam(value = "categoryId", defaultValue = "1") int categoryid,
                      HttpServletRequest request) {
        Article article = new Article();
        article.setTitle(title);
        article.setMessage(message);
        article.setImages(photo);
        article.setUserId(common.getid(request));
        article.setGoodNum(0);
        article.setCategoryId(categoryid);
        return articleService.insertSelective(article);
    }

    @RequestMapping(value = "update.do", method = RequestMethod.PUT)
    public Msg update(@RequestParam("articleId") int articleid,
                      @RequestParam("message") String message,
                      @RequestParam("title") String title,
                      @RequestParam("photo") String pho,
                      HttpServletRequest request) {
        Article article = new Article();
        article.setArticleId(articleid);
        article.setMessage(message);
        article.setTitle(title);
        article.setImages(pho);
        return articleService.updateSelective(article, common.getid(request));
    }

    @RequestMapping(value = "delete.do/{id}", method = RequestMethod.DELETE)
    public Msg delete(@PathVariable("id") int id, HttpServletRequest request) {
        return articleService.deleteById(id, common.getid(request));
    }

    @RequestMapping(value = "good.do/{id}", method = RequestMethod.PUT)
    public Msg update(@PathVariable("id") int id, HttpServletRequest request) {
        return articleService.goodbyid(id, common.getid(request));
    }


}