package com.formu.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by weiqiang
 */
public class FileUtil {

    public static String save(MultipartFile file){
        String name = String.valueOf(new Date().getTime()) + new Random(1000).nextInt() + ".png";
        File fie = new File("/root/tomcat1/webapps/ROOT/WEB-INF/classes/pho", name);
        try {
            file.transferTo(fie);
        } catch (IOException e) {
            return null;
        }
        return   "http://lizhinong.com/pho/" + name;

    }
}
