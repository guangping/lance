package com.framework.utils.html;

import com.framework.utils.html.jsoup.SishuokCleaner;
import com.framework.utils.html.jsoup.SishuokWhitelist;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-04-24 21:19
 * To change this template use File | Settings | File Templates.
 */
public class HtmlUtils {
    /**
     * 去除html代码
     * @param inputString
     * @return
     */
    public static String HtmltoText(String inputString) {
        String htmlStr = inputString; //含html标签的字符串
        String textStr ="";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        java.util.regex.Pattern p_ba;
        java.util.regex.Matcher m_ba;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
            String patternStr = "\\s+";

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签

            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签

            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签

            p_ba = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
            m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll(""); //过滤空格

            textStr = htmlStr;

        }catch(Exception e) {
            e.printStackTrace();
        }
        return textStr;//返回文本字符串
    }


    /**
     * 从html中移除不安全tag
     *
     * @param html
     * @return
     */
    public static String removeUnSafeTag(String html) {
        SishuokWhitelist whitelist = new SishuokWhitelist();
        whitelist.addTags("embed", "object", "param", "span", "div", "img", "font", "del");
        whitelist.addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup");
        whitelist.addTags("dd", "dl", "dt", "em", "hr", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img");
        whitelist.addTags("li", "ol", "p", "pre", "q", "small", "strike", "strong", "sub", "sup", "table");
        whitelist.addTags("tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul");

        //删除以on开头的（事件）
        whitelist.addAttributes(":all", "on");
        Document dirty = Jsoup.parseBodyFragment(html, "");
        SishuokCleaner cleaner = new SishuokCleaner(whitelist);
        Document clean = cleaner.clean(dirty);

        return clean.body().html();
    }

    /**
     * 删除指定标签
     *
     * @param html
     * @param tagName
     * @return
     */
    public static String removeTag(String html, String tagName) {
        Element bodyElement = Jsoup.parse(html).body();
        bodyElement.getElementsByTag(tagName).remove();
        return bodyElement.html();
    }

    /**
     * 获取html文档中的文本 并仅提取文本中的前maxLength个 超出部分使用……补充
     *
     * @param html
     * @param maxLength
     * @return
     */
    public static String text(String html, int maxLength) {
        String text = text(html);
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + "……";
    }

    /**
     * 获取html文档中的文本
     *
     * @return
     */
    public static String text(String html) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(html)) {
            return html;
        }
        return Jsoup.parse(removeUnSafeTag(html).replace("&lt;", "<").replace("&gt;", ">")).text();
    }
}
