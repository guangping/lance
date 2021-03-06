package com.github.lance.china;

import com.framework.utils.ChineseUtils;
import com.framework.utils.EnglishUtils;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-20 21:29
 * To change this template use File | Settings | File Templates.
 */
public class ChinaTest {

    @Test
    public void run() {
        String str = null;

        int hightPos, lowPos; // 定义高低位

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值

        lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值

        byte[] b = new byte[2];

        b[0] = (new Integer(hightPos).byteValue());

        b[1] = (new Integer(lowPos).byteValue());

        try {
            str = new String(b, "GBk");//转成中文

            System.err.println(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void convertPinyin() {
        try {
            System.out.println(ChineseUtils.converToEnameLower("重庆"));
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
    }

    @Test
    public void randomEnglish() {
        System.out.println(EnglishUtils.getRandomEngilish());
        System.out.println(EnglishUtils.getRandomEngilishLower());
    }

    @Test
    public void runStu() {
        String str = "java截取中英文混合字符串 等宽显示";
        try {
            //System.out.println(ChineseUtils.substring(str,6));
            //System.out.println(ChineseUtils.subString(str, 2, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runChineseLength() {
        String str = "a截取中";
        System.out.println(ChineseUtils.length(str));
    }

    @Test
    public void runInsertSub() {
        String str = "截取中英文混合字符串 等宽显示";
        try {

            System.out.println(ChineseUtils.insertStr(str,"<br/>",3));
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
