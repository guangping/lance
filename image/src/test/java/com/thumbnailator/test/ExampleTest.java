package com.thumbnailator.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Before;
import org.junit.Test;


public class ExampleTest {

    private String image_1 = "src/test/resources/images/a380_1280x1024.jpg";
    private String image_2 = "src/test/resources/images/watermark.png";
    private String to_dir = "F:/other/";

    @Before
    public void setUp() throws Exception {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Test
    public void yashuo() {
        String path = "F://other//IMG_0787.JPG";
        try {
            Thumbnails.of(path)
                    .scale(0.25f)
                    .toFile(to_dir + "IMG_0787_25%.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //按照比例进行缩放
    @Test
    public void scale() throws Exception {
        //scale(比例)
        Thumbnails.of(image_1)
                .scale(0.25f)
                .toFile(to_dir + "a380_25%.jpg");

        Thumbnails.of(image_2)
                .scale(1.10f)
                .toFile(to_dir + "a380_110%.jpg");
    }

    //指定大小进行缩放
    private void test1() throws Exception {
        //size(宽度, 高度)

        /*  
         * 若图片横比200小，高比300小，不变  
         * 若图片横比200小，高比300大，高缩小到300，图片比例不变  
         * 若图片横比200大，高比300小，横缩小到200，图片比例不变  
         * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300  
         */
        Thumbnails.of(image_1)
                .size(200, 300)
                .toFile(to_dir + "a380_200x300.jpg");

        Thumbnails.of(image_2)
                .size(2560, 2048)
                .toFile(to_dir + "a380_2560x2048.jpg");
    }


    //不按照比例，指定大小进行缩放
    @Test
    public void test3() throws Exception {
        //keepAspectRatio(false) 默认是按照比例缩放的
        Thumbnails.of(image_1)
                .size(200, 200)
                .keepAspectRatio(false)
                .toFile(to_dir + "/a380_200x200.jpg");
    }

    //旋转
    @Test
    public void test4() throws Exception {
        //rotate(角度),正数：顺时针 负数：逆时针
        Thumbnails.of(image_1)
                .size(1280, 1024)
                .rotate(90)
                .toFile(to_dir + "a380_rotate+90.jpg");

        Thumbnails.of(image_2)
                .size(1280, 1024)
                .rotate(-90)
                .toFile(to_dir + "a380_rotate-90.jpg");
    }

    //水印
    @Test
    public void test5() throws Exception {
        //watermark(位置，水印图，透明度)
        Thumbnails.of(image_1)
                .size(1280, 1024)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(image_2)), 0.5f)
                .outputQuality(0.8f)
                .toFile(to_dir + "a380_watermark_bottom_right.jpg");

        Thumbnails.of(image_2)
                .size(1280, 1024)
                .watermark(Positions.CENTER, ImageIO.read(new File(image_2)), 0.5f)
                .outputQuality(0.8f)
                .toFile(to_dir + "a380_watermark_center.jpg");
    }

    //裁剪
    public void test6() throws Exception {
        //sourceRegion()

        //图片中心400*400的区域
        Thumbnails.of("images/a380_1280x1024.jpg")
                .sourceRegion(Positions.CENTER, 400, 400)
                .size(200, 200)
                .keepAspectRatio(false)
                .toFile("c:/a380_region_center.jpg");

        //图片右下400*400的区域
        Thumbnails.of("images/a380_1280x1024.jpg")
                .sourceRegion(Positions.BOTTOM_RIGHT, 400, 400)
                .size(200, 200)
                .keepAspectRatio(false)
                .toFile("c:/a380_region_bootom_right.jpg");

        //指定坐标
        Thumbnails.of("images/a380_1280x1024.jpg")
                .sourceRegion(600, 500, 400, 400)
                .size(200, 200)
                .keepAspectRatio(false)
                .toFile("c:/a380_region_coord.jpg");
    }

    //转化图像格式
    @Test
    public void test7() throws Exception {
        //outputFormat(图像格式)
        Thumbnails.of("images/a380_1280x1024.jpg")
                .size(1280, 1024)
                .outputFormat("png")
                .toFile("c:/a380_1280x1024.png");

        Thumbnails.of("images/a380_1280x1024.jpg")
                .size(1280, 1024)
                .outputFormat("gif")
                .toFile("c:/a380_1280x1024.gif");
    }

    //输出到OutputStream
    @Test
    public void test8() throws Exception {
        //toOutputStream(流对象)
        OutputStream os = new FileOutputStream("c:/a380_1280x1024_OutputStream.png");
        Thumbnails.of("images/a380_1280x1024.jpg")
                .size(1280, 1024)
                .toOutputStream(os);
    }

    //输出到BufferedImage
    public void test9() throws Exception {
        //asBufferedImage() 返回BufferedImage
        BufferedImage thumbnail = Thumbnails.of("images/a380_1280x1024.jpg")
                .size(1280, 1024)
                .asBufferedImage();
        ImageIO.write(thumbnail, "jpg", new File("c:/a380_1280x1024_BufferedImage.jpg"));
    }

}
