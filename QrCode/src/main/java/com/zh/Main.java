package com.zh;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        BufferedImage 测试 = QrCodeUtil.generateQrCode("测试");
        System.out.println("");
        OutputStream outStream = null;

        String path = "E:/Code" + new Date().getTime() + ".png";//设置二维码的文件名

        try {
            outStream = new FileOutputStream(new File(path));
            // 目前 针对容错等级为H reduceWhiteArea 二维码空白区域的大小 根据实际情况设置，如果二维码内容长度不固定的话 需要自己根据实际情况计算reduceWhiteArea的大小
            ImageIO.write(测试, "PNG", outStream);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}