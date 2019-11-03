package com.formu.Service.imp;

import com.formu.Service.IMesService;
import com.formu.Utils.Msg;
import com.formu.bean.vo.Mes;
import com.formu.bean.po.FollowInfo;
import com.formu.bean.po.MesInfo;
import com.formu.mapper.MesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by weiqiang
 */
@Service
public class MesService implements IMesService {


    @Autowired
    private MesMapper mesMapper;

    @Override
    public Msg getMesUsers(int userid) {
        if (userid != 0) {
            List<FollowInfo> users = mesMapper.getMessages(userid);
            if (users != null)
                return Msg.createBySuccess(users);
            else
                return Msg.createByError();
        }
        return Msg.createByError();
    }

    @Override
    public Msg getSendMesUsers(int userid) {
        if (userid != 0) {
            List<FollowInfo> users = mesMapper.getSendMessages(userid);
            if (users != null)
                return Msg.createBySuccess(users);
            else
                return Msg.createByError();
        }
        return Msg.createByError();
    }


    @Override
    public Msg getById(int mesId, int userId) {
        if (mesId != 0) {
            MesInfo mesInfo = mesMapper.selectByPrimaryKey2(mesId);
            if (mesInfo == null)
                return Msg.createByErrorMessage("获取私信失败");
            if (mesInfo.getIsread() == 0 && mesInfo.getToUserId() == userId) {
                mesInfo.setIsread(1);
                Mes mes = new Mes();
                mes.setMesIsread(1);
                mes.setMesId(mesId);
                mesMapper.updateByPrimaryKeySelective(mes);
            }
            return Msg.createBySuccess(mesInfo);
        }
        return Msg.createByError();
    }

    @Override
    public Msg deleteByid(int mesId, int userId) {
        if (mesId != 0 && userId != 0) {

            Mes mes = mesMapper.selectByPrimaryKey(mesId);
            if (mes.getMesToid() == userId) {

                if (mes.getMesFromid() == 0)
                    if (mesMapper.deleteByPrimaryKey(mesId) > 0)
                        return Msg.createBySuccessMessage("私信删除成功");
                    else
                        return Msg.createByErrorMessage("删除私信失败");

                mes.setMesToid(0);
                if (mesMapper.updateByPrimaryKeySelective(mes) > 0)
                    return Msg.createBySuccessMessage("私信删除成功");
                else
                    return Msg.createByErrorMessage("删除私信失败");
            } else if (mes.getMesFromid() == userId) {
                if (mes.getMesToid() == 0)
                    if (mesMapper.deleteByPrimaryKey(mesId) > 0)
                        return Msg.createBySuccessMessage("私信删除成功");
                    else
                        return Msg.createByErrorMessage("删除私信失败");

                mes.setMesFromid(0);
                if (mesMapper.updateByPrimaryKeySelective(mes) > 0)
                    return Msg.createBySuccessMessage("私信删除成功");
                else
                    return Msg.createByErrorMessage("删除私信失败");
            }
            else
                return Msg.createByErrorMessage("删除私信失败");
        }
        return Msg.createByErrorMessage("删除私信失败");
    }

    @Override
    public Msg insert(Mes mes) {
        if (mes != null) {
            int ok = mesMapper.insertSelective(mes);
            if (ok > 0)
                return Msg.createBySuccessMessage("发送私信成功");
            else
                return Msg.createByError();
        }
        return Msg.createByErrorMessage("发送私信失败");
    }


}


