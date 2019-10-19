package com.formu.Service.imp;

import com.formu.Service.IArticleService;
import com.formu.Utils.Msg;
import com.formu.bean.Article;
import com.formu.bean.ArticleGood;
import com.formu.bean.User;
import com.formu.mapper.ArticleGoodMapper;
import com.formu.mapper.ArticleMapper;
import com.formu.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by weiqiang
 */
@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleGoodMapper articleGoodMapper;

    @Override
    public Msg getArticleByPage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Article> articleList = articleMapper.selectall();
        PageInfo<Article> pageResult = new PageInfo<>(articleList);
        return Msg.createBySuccess(pageResult);

    }

    @Override
    public Msg getArticleById(int articleid, int userid) {
        Article article = articleMapper.selectByPrimaryKey(articleid);

        if (article == null) {
            return Msg.createByErrorMessage("没有该文章");
        }
        if (userid != 0) {
            ArticleGood articleGood = articleGoodMapper.selectByUserAndArticle(userid, articleid);
            if (articleGood != null) {
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
    public Msg goodbyid(int articleid, int userid) {
        ArticleGood articleGood = articleGoodMapper.selectByUserAndArticle(userid, articleid);
        if (articleGood == null) {
            int ok1 = articleGoodMapper.insertSelective(new ArticleGood(null, articleid, userid));
            int ok2 = articleMapper.updateGoodNumById(articleid, 1);
            if (ok1 > 0 && ok2 > 0)
                return Msg.createBySuccessMessage("点赞成功！");
            else
                return Msg.createBySuccessMessage("点赞失败！");
        } else {
            int ok3 = articleGoodMapper.deleteByPrimaryKey(articleGood.getAgId());
            int ok4 = articleMapper.updateGoodNumById(articleid, -1);
            if (ok3 > 0 && ok4 > 0)
                return Msg.createBySuccessMessage("取消点赞成功！");
            else
                return Msg.createBySuccessMessage("点赞失败！");
        }
    }
}
