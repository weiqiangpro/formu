package com.formu.Control;

import com.formu.Service.IArticleService;
import com.formu.Service.imp.ArticleService;
import com.formu.Utils.Msg;
import com.formu.bean.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/article/")
public class ArticleControl {

    @Autowired
    private IArticleService articleService;

    @Autowired
    StringRedisTemplate redis;

    @RequestMapping(value = "get/{page}", method = RequestMethod.GET)
    public Msg getall(@PathVariable("page") int page) {
        redis.opsForValue().set("bb","bb",50, TimeUnit.SECONDS);
        return articleService.getArticleByPage(page, 10);
    }

    @RequestMapping(value = "getbyid/{id}", method = RequestMethod.GET)
    public Msg getById(@PathVariable("id") int id) {
        return articleService.getArticleById(id);
    }

    @RequestMapping(value = "getbycategory/{categoryid}/{page}", method = RequestMethod.GET)
    public Msg getByCategory(@PathVariable("categoryid") int categoryid, @PathVariable("page") int page) {
        System.out.println(categoryid + "    " + page);
        return articleService.getArticleByCategory(page, 10, categoryid);
    }

    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public Msg insert(Article article) {
        return articleService.insertSelective(article);
    }

    @RequestMapping(value = "update.do", method = RequestMethod.PUT)
    public Msg update(Article article) {
        return articleService.updateSelective(article);
    }

    @RequestMapping(value = "delete.do/{id}", method = RequestMethod.DELETE)
    public Msg delete(@PathVariable("id") int id) {
        return articleService.deleteById(id);
    }

}