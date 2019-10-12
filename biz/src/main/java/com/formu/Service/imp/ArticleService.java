package com.formu.Service.imp;

import com.formu.Service.IArticleService;
import com.formu.Utils.Msg;
import com.formu.Utils.SetUtil;
import com.formu.bean.Article;
import com.formu.bean.User;
import com.formu.mapper.ArticleMapper;
import com.formu.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by weiqiang
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Msg getArticleByPage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Article> articleList = articleMapper.selectall();
        PageInfo<Article> pageResult = new PageInfo<>(articleList);
        return Msg.createBySuccess(pageResult);

    }

    @Override
    public Msg getArticleById(int id, int userid) {
        Article article = articleMapper.selectByPrimaryKey(id);

        if (article == null) {
            return Msg.createByErrorMessage("没有该文章");
        }
        if (userid != 0) {
            User user = userMapper.selectByPrimaryKey(userid);
            List<String> list = Arrays.asList(user.getArticleGood().split("-"));
            if (list.contains(String.valueOf(id))) {
                article.setIsgood(true);
            }
        }
        return Msg.createBySuccess(article);
    }

    @Override
    public Msg getArticleByCategory(int pageNum, int pageSize, int id) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articleList = articleMapper.selectByCategory(id);
        PageInfo<Article> pageResult = new PageInfo<>(articleList);
        return Msg.createBySuccess(pageResult);
    }

    @Override
    public Msg insertSelective(Article article) {
        if (article != null) {
            int ok = articleMapper.insertSelective(article);
            if (ok > 0) {
                return Msg.createBySuccessMessage("添加文章成功!");
            }
            return Msg.createByErrorMessage("添加文章失败!");
        }
        return Msg.createByErrorMessage("添加文章失败!");
    }

    @Override
    public Msg updateSelective(Article article, int userid) {
        if (article == null)
            return Msg.createByErrorMessage("修改文章失败!");

        Article article1 = articleMapper.selectByPrimaryKey(article.getArticleId());
        if (article1 == null)
            return Msg.createByErrorMessage("修改文章失败!不存在该文章");


        if (userid != article1.getUserId())
            return Msg.createByErrorMessage("修改文章失败!您不是本文章的作者,无权修改！");

        int ok = articleMapper.updateByPrimaryKeySelective(article);

        if (ok > 0)
            return Msg.createBySuccessMessage("修改文章成功!");

        return Msg.createByErrorMessage("修改文章失败!");
    }

    @Override
    public Msg deleteById(int id, int userid) {

        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null)
            return Msg.createByErrorMessage("不存在该文章");

        if (userid != article.getUserId())
            return Msg.createByErrorMessage("删除文章失败!您不是本文章的作者，无权删除该文章!");

        int ok = articleMapper.deleteByPrimaryKey(id);
        if (ok > 0) {
            return Msg.createBySuccessMessage("删除文章成功!");
        }
        return Msg.createByErrorMessage("删除文章失败!");
    }

    @Override
    public Msg goodbyid(int id, int userid) {

        User user = userMapper.selectByPrimaryKey(userid);
        String good1 = userMapper.selectByPrimaryKey(userid).getArticleGood();
        String good2 = SetUtil.upGood(good1, id);
        int n1 = good1 == null ? 0:good1.length();
        int n2 = good2 == null ? 0:good2.length();
        if (n1 == n2)
            return Msg.createByErrorMessage("点赞失败");
        user.setArticleGood(good2);


        int ok2 = userMapper.updateByPrimaryKeySelective(user);
        int ok1 = articleMapper.updateGoodNumById(id, n2 > n1 ? 1 : -1);
        if (ok1 > 0 && ok2 > 0) {
            if (n2 > n1)
                return Msg.createByErrorMessage("点赞成功！");
            else
                return Msg.createBySuccessMessage("取消点赞成功！");
        }

        return Msg.createBySuccessMessage("点赞失败！");

    }
}
