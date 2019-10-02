package com.formu.Service;

import com.formu.Utils.Msg;
import com.formu.bean.Category;

/**
 * Created by weiqiang
 */
public interface ICategoryService {

    public Msg getall();

    public Msg insert(Category category);

    public Msg update(Category category);

    public Msg delete(int id);
}
