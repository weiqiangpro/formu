package com.formu.Service.imp;

import com.formu.Service.IUserService;
import com.formu.Utils.Msg;
import com.formu.bean.User;
import com.formu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by weiqiang
 */
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(User user) {
        return 0;
    }

    @Override
    public int updateArticleNum(int id) {
        return userMapper.updateArticleNumByPrimaryKey(id);
    }

    @Override
    public int updateGoodNum(int articleId,int user_id) {
        return userMapper.updateGoodNumByPrimaryKey("-"+String.valueOf(articleId),user_id);
    }


}
