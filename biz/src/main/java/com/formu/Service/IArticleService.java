package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.Article;

import java.awt.*;

/**
 * Created by weiqiang
 */
public interface IArticleService {

    public Msg getArticleByPage(int pageNum,int pageSize);

    public Msg getArticleById(int id);

    public Msg getArticleByCategory(int pageNum,int pageSize,int id);

    public Msg insertSelective(Article article);

    public Msg updateSelective(Article article);

    public Msg deleteById(int id);


}
