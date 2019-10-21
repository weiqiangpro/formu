package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.Article;

import java.awt.*;

/**
 * Created by weiqiang
 */
public interface IArticleService {

    Msg getArticleByPage(int pageNum, int pageSize);

    Msg getArticleById(int id, int userid);

    Msg getArticleByUserId(int pageNum, int pageSize, int userId);

    Msg insertSelective(Article article);

    Msg updateSelective(Article article, int userid);

    Msg deleteById(int id, int userid);

    Msg goodbyid(int id, int userid);

    Msg topByGood(int pageNum, int pageSize);

    Msg topByGoodAndComment(int pageNum, int pageSize);

    Msg topByComment(int pageNum, int pageSize);

}
