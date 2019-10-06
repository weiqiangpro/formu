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
@RestController
public class FileControl {

    @RequestMapping(value = "/upfile/{id}", method = RequestMethod.POST)
    public Msg file(@RequestParam(value = "file",required = true) MultipartFile file, @PathVariable("id") int id) {
        if (file.isEmpty()) {
            return Msg.createByError();
        }
        String name = String.valueOf(new Date().getTime())+new Random(1000).nextInt()+ ".png";
        File fie = new File("./webapps/ht/pic",name);
        try {
            file.transferTo(fie);
        } catch (IOException e) {
             return Msg.createByError();
        }
        return Msg.createBySuccessMessage(name);
    }


}
