package com.formu.Control;

import com.formu.Service.IArticleService;
import com.formu.Service.imp.ArticleService;
import com.formu.Utils.JsonUtil;
import com.formu.Utils.JwtToken;
import com.formu.Utils.Msg;
import com.formu.bean.Article;
import com.formu.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

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
    StringRedisTemplate redis;


    @RequestMapping(value = "get/{page}", method = RequestMethod.GET)
    public Msg getall(@PathVariable("page") int page) {
//        redis.opsForValue().set("bb","bb",50, TimeUnit.SECONDS);
        return articleService.getArticleByPage(page, 10);
    }

    @RequestMapping(value = "getbyid/{id}", method = RequestMethod.GET)
    public Msg getById(@PathVariable("id") int id, HttpServletRequest request) {
        int userid = getid(request);
        return articleService.getArticleById(id, userid);
    }

    @RequestMapping(value = "getbycategory/{categoryid}/{page}", method = RequestMethod.GET)
    public Msg getByCategory(@PathVariable("categoryid") int categoryid, @PathVariable("page") int page) {
        System.out.println(categoryid + "    " + page);
        return articleService.getArticleByCategory(page, 10, categoryid);
    }

    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public Msg insert(Article article, HttpServletRequest request) {
        article.setUserId(getid(request));
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

    @RequestMapping(value = "good.do/{id}", method = RequestMethod.PUT)
    public Msg update(@PathVariable("id") int id, HttpServletRequest request) {
        return articleService.goodbyid(id, getid(request));
    }

    private int getid(HttpServletRequest request) {
        String header = request.getHeader("Token");
        if (StringUtils.isBlank(header)) {
            return 0;
        }
        String token = redis.opsForValue().get(header);
        User user = JsonUtil.string2Obj(token, User.class);
        if (user == null) {
            return 0;
        }
        return user.getUserId();
    }

}