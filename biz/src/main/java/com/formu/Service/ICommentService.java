package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.Article;
import com.formu.bean.Comment;

/**
 * Created by weiqiang
 */
public interface ICommentService {

    Msg getCommentyArticleAndisParent(int pageNum, int pageSize, int id);

    Msg insertNotParent(Comment comment);

    Msg insertIsParent(Comment comment);

    Msg deleteById(int id, int userid);

    Msg goodbyid(int id, int userid);
}
