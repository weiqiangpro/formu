package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.Mes;

/**
 * Created by weiqiang
 */
public interface IMesService {

    Msg getMesUsers(int userid);

    Msg getById(int mesId);

    Msg deleteByid(int mesId, int userId);

    Msg insert(Mes mes);
}
