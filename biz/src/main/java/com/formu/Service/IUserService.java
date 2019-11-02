package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.vo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by weiqiang
 */
public interface IUserService {

    Msg getOtherById(int otherId, int userId);

    Msg getMyByid(int id);

    Msg register(User user, String code);

    Msg updatepasswd(String oldpasswd, String newpasswd1, String newpasswd2, int userid);

    Msg findpasswd(String account, String passwd1, String passwd2, String code);

    Msg getbyaccout(String accout);

    Msg addFollow(int otherid, int userid);

    Msg getFollows(int userid);

    Msg getFolloweds(int userid);

    Msg isregister(String accout);

    Msg modifyInformation(User user);

    Msg logout(HttpServletRequest request);
}
