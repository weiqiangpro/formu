package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.Article;
import com.formu.bean.Comment;

/**
 * Created by weiqiang
 */
public interface ICommentService {

    public Msg getCommentyArticleAndisParent(int pageNum, int pageSize, int id);

    public Msg insertNotParent(Comment comment);

    public Msg insertIsParent(Comment comment);

    public Msg deleteById(int id,int userid);

    public Msg goodbyid(int id, int userid);
}
