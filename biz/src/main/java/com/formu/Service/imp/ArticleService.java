package com.formu.Service.imp;

import com.formu.Service.IArticleService;
import com.formu.Utils.Msg;
import com.formu.bean.Article;
import com.formu.mapper.ArticleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by weiqiang
 */
@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Msg getArticleByPage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Article> articleList = articleMapper.selectall();
        PageInfo<Article> pageResult = new PageInfo<>(articleList);
        return Msg.createBySuccess(pageResult);

    }

    @Override
    public Msg getArticleById(int id) {

        return Msg.createBySuccess(articleMapper.selectByPrimaryKey(id));
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
        if (article!=null) {
            int ok = articleMapper.insertSelective(article);
            if (ok > 0) {
                return Msg.createBySuccessMessage("添加文章成功!");
            }
            return Msg.createByErrorMessage("添加文章失败!");
        }
        return Msg.createByErrorMessage("添加文章失败!");
    }

    @Override
    public Msg updateSelective(Article article) {
        if (article!=null) {
            int ok = articleMapper.updateByPrimaryKeySelective(article);
            if (ok > 0) {
                return Msg.createBySuccessMessage("修改文章成功!");
            }
            return Msg.createByErrorMessage("修改文章失败!");
        }
        return Msg.createByErrorMessage("修改文章失败!");
    }

    @Override
    public Msg deleteById(int id) {
            int ok = articleMapper.deleteByPrimaryKey(id);
            if (ok > 0) {
                return Msg.createBySuccessMessage("删除文章成功!");
            }
            return Msg.createByErrorMessage("删除文章失败!");
    }
}
