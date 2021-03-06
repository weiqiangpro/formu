package com.formu.Service.imp;

import com.formu.Service.ICategoryService;
import com.formu.Utils.Msg;
import com.formu.bean.vo.Category;
import com.formu.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by weiqiang
 */

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Msg getall() {
        return Msg.createBySuccess(categoryMapper.selectall());
    }

    @Override
    public Msg insert(Category category) {
        if (category != null) {
            int ok = categoryMapper.insertSelective(category);
            if (ok > 0) {
                return Msg.createBySuccessMessage("添加分类成功!");
            }
            return Msg.createByErrorMessage("添加分类失败!");
        }
        return Msg.createByErrorMessage("添加分类失败!");
    }

    @Override
    public Msg update(Category category) {
        if (category != null) {
            int ok = categoryMapper.updateByPrimaryKeySelective(category);
            if (ok > 0) {
                return Msg.createBySuccessMessage("修改分类成功!");
            }
            return Msg.createByErrorMessage("修改分类失败!");
        }
        return Msg.createByErrorMessage("修改分类失败!");
    }

    @Override
    public Msg delete(int id) {
        int ok = categoryMapper.deleteByPrimaryKey(id);
        if (ok > 0) {
            return Msg.createBySuccessMessage("删除分类成功!");
        }
        return Msg.createByErrorMessage("删除分类失败!");
    }

}
