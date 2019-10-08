package com.formu.Control;

import com.formu.Service.IArticleService;
import com.formu.Service.ICategoryService;
import com.formu.Utils.Msg;
import com.formu.bean.Article;
import com.formu.bean.Category;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/category/")
@CrossOrigin
public class CategoryControl {

    @Autowired
    private ICategoryService categoryService;

    @ApiOperation(value = "获取分类,需要登录", notes = "")
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public Msg getall(){
        return categoryService.getall();
    }
}