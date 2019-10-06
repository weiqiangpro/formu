package com.formu.Control;

import com.formu.Service.imp.UserService;
import com.formu.Utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/user/")
public class UserControl {

    @Autowired
    private UserService userService;
    @RequestMapping("other/{id}")
    public Msg getother(@PathVariable("id")int userid){
        return userService.getOtherById(userid);
    }
    @RequestMapping("me.do/{id}")
    public Msg getme(@PathVariable("id")int userid){
        return userService.getMyByid(userid);
    }
}
