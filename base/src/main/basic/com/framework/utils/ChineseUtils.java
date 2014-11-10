package com.framework.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-20 21:35
 * To change this template use File | Settings | File Templates.
 */
public class ChineseUtils {

    /*
    * 获取随机汉字
    * */
    public static String getRandomChinese() {
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /*
    * 将所有汉字转换成全拼音输出
    * */
    public static String converToEnameUpper(String name) throws BadHanyuPinyinOutputFormatCombination {
        StringBuilder succeedPinyin = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大小写输出
        //如果不想要音调 删除就行
     /*   format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);//音调设置
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);//音调*/
        char[] ar = name.toCharArray();
        for (int i = 0; i < ar.length; i++) {
            String[] a = PinyinHelper.toHanyuPinyinStringArray(ar[i], format);
            if (null != a)
                succeedPinyin.append(a[0]);
            else succeedPinyin.append(ar[i]);
        }
        return succeedPinyin.toString();
    }

    /*
   * 将所有汉字转换成全拼音输出
   * */
    public static String converToEnameLower(String name) throws BadHanyuPinyinOutputFormatCombination {
        StringBuilder succeedPinyin = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);//大小写输出
        //如果不想要音调 删除就行
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
     /*   format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);//音调设置
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);//音调*/
        char[] ar = name.toCharArray();
        for (int i = 0; i < ar.length; i++) {
            String[] a = PinyinHelper.toHanyuPinyinStringArray(ar[i], format);
            if (null != a)
                succeedPinyin.append(a[0]);
            else succeedPinyin.append(ar[i]);
        }
        return succeedPinyin.toString();
    }

    /*
    *截取字符串 中文
    * */
    public static String substring(String text, int i, int length)
            throws UnsupportedEncodingException {
        if (text == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int currentLength = 0;
        for (char c : text.toCharArray()) {
            currentLength += String.valueOf(c).getBytes("GBK").length;
            if (currentLength <= length) {
                sb.append(c);
            } else {
                break;
            }
        }
        return sb.toString();
    }

    public static String subString(String text, int length, String endWith) {
        int textLength = text.length();
        int byteLength = 0;
        StringBuffer returnStr = new StringBuffer();
        for (int i = 0; i < textLength && byteLength < length * 2; i++) {
            String str_i = text.substring(i, i + 1);
            if (str_i.getBytes().length == 1) {//英文
                byteLength++;
            } else {//中文
                byteLength += 2;
            }
            returnStr.append(str_i);
        }
        try {
            if (byteLength < text.getBytes("GBK").length) {//getBytes("GBK")每个汉字长2，getBytes("UTF-8")每个汉字长度为3
                returnStr.append(endWith);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnStr.toString();
    }


    /*获取字符串长度*/
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

}
