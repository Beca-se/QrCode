package com.zh;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Hashtable;


/**
 * 生成二维码
 */

public class QrCodeUtil {


    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    private static final int margin = 0;

    private static final int LogoPart = 4;
    private static final String format = "PNG";

    public static BufferedImage generateQrCode(String content, String logoPath, int width, int height) {
        try {
            BitMatrix bitMatrix = setBitMatrix(content, width, height);


            BufferedImage image = toBufferedImage(bitMatrix, 2);

            // 如果设置了二维码里面的logo 加入LOGO水印
            if (!StringUtils.isEmpty(logoPath)) {

                image = addLogo(image, logoPath);

            }
            return image;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static BufferedImage generateQrCode(String content, String logoPath) {
        return generateQrCode(content, null, 1000, 1000);
    }

    public static BufferedImage generateQrCode(String content) {
        return generateQrCode(content, null);

    }


    public static void main(String[] args) throws WriterException {

//二维码内容

        String content = "https://www.baidu.com";

        String logoPath = "E:\\log.png"; // 二维码中间的logo信息 非必须

        String format = "PNG";

        int width = 1200; // 二维码宽度

        int height = 1200;// 二维码高度

// 设置二维码矩阵的信息

        BitMatrix bitMatrix = setBitMatrix(content, width, height);

// 设置输出流



    }


    /**
     * 设置生成二维码矩阵信息
     *
     * @param content 二维码图片内容
     * @param width   二维码图片宽度
     * @param height  二维码图片高度
     * @throws WriterException
     */

    private static BitMatrix setBitMatrix(String content, int width, int height) throws WriterException {

        BitMatrix bitMatrix = null;

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();

        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 指定编码方式,避免中文乱码

        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 指定纠错等级 如果二维码里面的内容比较多的话推荐使用H 容错率30%， 这样可以避免一些扫描不出来的问题

        hints.put(EncodeHintType.MARGIN, margin); // 指定二维码四周白色区域大小 官方的这个方法目前没有没有作用默认设置为0

        bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        return bitMatrix;

    }




    /**
     * @param matrix
     * @param reduceWhiteArea
     * @return
     */

    private static BufferedImage toBufferedImage(BitMatrix matrix, int reduceWhiteArea) {

        int width = matrix.getWidth();

        int height = matrix.getHeight();

        BufferedImage image = new BufferedImage(width - 2 * reduceWhiteArea, height - 2 * reduceWhiteArea, BufferedImage.TYPE_3BYTE_BGR);

        for (int x = reduceWhiteArea; x < width - reduceWhiteArea; x++) {

            for (int y = reduceWhiteArea; y < height - reduceWhiteArea; y++) {

                image.setRGB(x - reduceWhiteArea, y - reduceWhiteArea, matrix.get(x, y) ? BLACK : WHITE);

            }

        }
        return image;

    }


    /**
     * 给二维码图片中绘制logo信息 非必须
     *
     * @param image    二维码图片
     * @param logoPath logo图片路径
     */

    private static BufferedImage addLogo(BufferedImage image, String logoPath) throws IOException {

        Graphics2D g = image.createGraphics();

        BufferedImage logoImage = ImageIO.read(new File(logoPath));

// 计算logo图片大小,可适应长方形图片,根据较短边生成正方形

        int width = image.getWidth() < image.getHeight() ? image.getWidth() / LogoPart : image.getHeight() / LogoPart;

        int height = width;

// 计算logo图片放置位置

        int x = (image.getWidth() - width) / 2;

        int y = (image.getHeight() - height) / 2;

// 在二维码图片上绘制中间的logo

        g.drawImage(logoImage, x, y, width, height, null);

// 绘制logo边框,可选

        g.setStroke(new BasicStroke(2)); // 画笔粗细

        g.setColor(Color.BLUE); // 边框颜色

        g.drawRect(x, y, width, height); // 矩形边框

        logoImage.flush();

        g.dispose();

        return image;

    }


}