package com.formu.Control;

import com.formu.Utils.Msg;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by weiqiang
 */
//@RestController
//@CrossOrigin
public class FileControl {

//    @RequestMapping(value = "/upfile", method = RequestMethod.POST)
//    public Msg file(@RequestParam(value = "file",required = true) MultipartFile file,@RequestParam(value = "id",defaultValue = "ada")String id) {
//            System.out.println("_______"+id+"_________");
//        if (file.isEmpty()) {
//            return Msg.createByError();
//        }
//        String name = String.valueOf(new Date().getTime())+new Random(1000).nextInt()+ ".png";
//        File fie = new File("/log",name);
//        try {
//            file.transferTo(fie);
//        } catch (IOException e) {
//             return Msg.createByError();
//        }
//        return Msg.createBySuccessMessage(name);
//    }
//
//    @RequestMapping("aa")
//    public String sa(){
//        return "hello";
//    }
}
