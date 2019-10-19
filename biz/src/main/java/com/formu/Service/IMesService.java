package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.Mes;

/**
 * Created by weiqiang
 */
public interface IMesService {

    public Msg getMesUsers(int userid);

    public Msg getById(int mesId);

    public Msg deleteByid(int mesId,int userId);

    public Msg insert(Mes mes);
}
