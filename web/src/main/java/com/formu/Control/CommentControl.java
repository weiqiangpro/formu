package com.formu.Control;

import com.formu.Service.ICategoryService;
import com.formu.Service.ICommentService;
import com.formu.Utils.Msg;
import com.formu.bean.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/comment/")
public class CommentControl {

    @Autowired
    private ICommentService commentService;

    @RequestMapping(value = "get/{id}/{pagenum}",method = RequestMethod.GET)
    public Msg getall(@PathVariable("id")int id,@PathVariable("pagenum")int pagenum){
        return commentService.getCommentyArticleAndisParent(pagenum,10,id);
    }


//    @RequestMapping(value = "insert.do",method = RequestMethod.POST)
//    public Msg insert(Category category){
//        return categoryService.insert(category);
//    }
//
//    @RequestMapping(value = "update.do",method = RequestMethod.PUT)
//    public Msg update(Category category){
//        return categoryService.update(category);
//    }
//
//    @RequestMapping(value = "delete.do/{id}",method = RequestMethod.DELETE)
//    public Msg delete(@PathVariable("id")int id){
//        return categoryService.delete(id);
//    }

}