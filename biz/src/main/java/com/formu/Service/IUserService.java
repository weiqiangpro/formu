package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.User;

/**
 * Created by weiqiang
 */
public interface IUserService {

    public Msg getOtherById(int otherId,int userId);

    public Msg getMyByid(int id);

    public Msg register(User user, String code);

    public Msg updatepasswd(String oldpasswd, String newpasswd1, String newpasswd2, int userid);

    public Msg findpasswd(String account, String passwd1, String passwd2, String code);

    public Msg getbyaccout(String accout);

     public  Msg addFollow(int otherid,int  userid);

    public Msg getFollows(int userid);

    public Msg getFolloweds(int userid);
}
