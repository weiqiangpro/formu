package com.formu.Service.imp;

import com.formu.Service.IUserService;
import com.formu.Utils.Msg;
import com.formu.bean.User;
import com.formu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by weiqiang
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Msg getOtherById(int id) {
        User user = userMapper.selectOtherByPrimaryKey(id);
        if (user != null){
            return Msg.createBySuccess(user);
        }
        return Msg.createByErrorMessage("没有此用户");
    }

    @Override
    public Msg getMyByid(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null){
            return Msg.createBySuccess(user);
        }
        return Msg.createByErrorMessage("没有此用户");
    }
}


