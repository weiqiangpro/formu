package com.formu.common;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class ReturnImgWH {
    //读取远程url图片,得到宽高  
    public static int returnImgWH(String imgurl) {
        boolean b=false;
        OutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            //实例化url  
            URL url = new URL(imgurl);
            //载入图片到输入流
             bis = new BufferedInputStream(url.openStream());
            //实例化存储字节数组  
            byte[] bytes = new byte[100];
            //设置写入路径以及图片名称  
            bos = new FileOutputStream(new File("C:\\thetempimg.gif"));
            int len;
            while ((len = bis.read(bytes)) > 0) {
                bos.write(bytes, 0, len);
            }

            //关闭输出流  
            b=true;
        } catch (Exception e) {
            //如果图片未找到  
            b=false;
        }finally {
            try {
                if (bis != null) {
                    bis.close();
                    bos.flush();
                }
                if (bos != null) {
                    bos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        int[] a = new int[2];
        if(b){//图片存在  
            //得到文件  
        File file = new java.io.File("C:\\thetempimg.gif");
            BufferedImage bi = null;
            boolean imgwrong=false;
            try {
                //读取图片  
                bi = javax.imageio.ImageIO.read(file);
                try{
                    //判断文件图片是否能正常显示,有些图片编码不正确  
                    int i = bi.getType();
                    imgwrong=true;
                }catch(Exception e){
                    imgwrong=false;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if(imgwrong){
                a[0] = bi.getWidth(); //获得 宽度  
                a[1] = bi.getHeight(); //获得 高度  
            }else{
                a=null;
            }
            bi=null;
            //删除文件  
            file.delete();
            file = null;
        }else{//图片不存在  
            a=null;
        }
        return a[1]*280/a[0];

    }

    public static void main(String[] args) {
        ReturnImgWH i = new ReturnImgWH();
        int a=i.returnImgWH("http://www.baidu.com/img/logo-yy.gif");
        if(a==0){
            System.out.println("图片未找到!");
        }else{
            System.out.println("宽为" + a);
        }
    }
}  