package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.Comment;
import com.formu.bean.User;

/**
 * Created by weiqiang
 */
public interface IUserService {

    public Msg getOtherById(int id);

    public Msg getMyByid(int id);
    public Msg register(User user,String code);
//    public int insert(User user);
//
//    public int updateArticleNum(int id);
//
//    public int  updateGoodNum(int articleId,int user_id);
}
