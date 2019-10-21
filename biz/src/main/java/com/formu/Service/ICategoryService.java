package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.Category;

/**
 * Created by weiqiang
 */
public interface ICategoryService {

    Msg getall();

    Msg insert(Category category);

    Msg update(Category category);

    Msg delete(int id);
}
