package com.flickr.test.html;

import com.flickr.db.pool.DBExecutors;
import com.flickr.db.pool.IDBExecutors;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-09-01 15:40
 * To change this template use File | Settings | File Templates.
 */
public class HtmlTest {
    private String url = "http://news.163.com/special/00011K6L/rss_newstop.xml";
    private Connection connection = null;
    private Document document = null;
    private IDBExecutors executors = null;
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);

    private List<String> urls = null;

    @BeforeMethod
    public void setUp() {
        try {
            this.initUrl();
            System.setProperty("DataConfig", "F:\\git\\lance\\trunk\\flickr\\src\\main\\resources\\jdbc.properties");
            executors = new DBExecutors();

            connection = Jsoup.connect(url);
            document = connection.userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1 ").get();

            //  document = Jsoup.parse(new File("F:/other/paipai/www.paipai.com/nvren/index.html~ptag=20316.39.1.html"), "gbk");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initUrl() {
        urls = new ArrayList<String>();
        urls.add("http://auto.163.com/special/00081K7D/rsstest.xml");
        urls.add("http://auto.163.com/special/00081K7D/rss_autouse.xml");
        urls.add("http://news.163.com/special/00011K6L/rss_newstop.xml");
        urls.add("http://news.163.com/special/00011K6L/rss_gj.xml");
        urls.add("http://news.163.com/special/00011K6L/rss_sh.xml");
        urls.add("http://news.163.com/special/00011K6L/rss_war.xml");
        urls.add("http://news.163.com/special/00011K6L/rss_hotnews.xml");
        urls.add("http://news.163.com/special/00011K6L/rss_newsspecial.xml");
        urls.add("http://news.163.com/special/00011K6L/rss_photo.xml");
        urls.add("http://news.163.com/special/00011K6L/rss_discovery.xml");
        urls.add("http://sports.163.com/special/00051K7F/rss_sportslq.xml");
        urls.add("http://sports.163.com/special/00051K7F/rss_sportscba.xml");
        urls.add("http://sports.163.com/special/00051K7F/rss_sportsyc.xml");
        urls.add("http://sports.163.com/special/00051K7F/rss_sportsyj.xml");
        urls.add("http://sports.163.com/special/00051K7F/rss_sportsgj.xml");
        urls.add("http://sports.163.com/special/00051K7F/rss_sportszg.xml");
        urls.add("http://sports.163.com/special/00051K7F/rss_sportszh.xml");
        urls.add("http://sports.163.com/special/00051K7F/rss_sportstennis.xml");
        urls.add("http://sports.163.com/special/00051K7F/rss_sportscp.xml");
        urls.add("http://ent.163.com/special/00031K7Q/rss_toutiao.xml");
        urls.add("http://ent.163.com/special/00031K7Q/rss_entstar.xml");
        urls.add("http://ent.163.com/special/00031K7Q/rss_entmovie.xml");
        urls.add("http://ent.163.com/special/00031K7Q/rss_enttv.xml");
        urls.add("http://ent.163.com/special/00031K7Q/rss_entpic.xml");
        urls.add("http://v.163.com/special/008544NC/vheadline.xml");
        urls.add("http://v.163.com/special/008544NC/vnewsrss.xml");
        urls.add("http://v.163.com/special/008544NC/tvrss.xml");
        urls.add("http://v.163.com/special/008544NC/moiverss.xml");
        urls.add("http://v.163.com/special/008544NC/vdocrss.xml");
        urls.add("http://v.163.com/special/008544NC/vheadline.xml");
        urls.add("http://money.163.com/special/00252EQ2/toutiaorss.xml");
        urls.add("http://money.163.com/special/00252EQ2/yaowenrss.xml");
        urls.add("http://money.163.com/special/00252EQ2/macrorss.xml");
        urls.add("http://money.163.com/special/00252EQ2/gushinewsrss.xml");
        urls.add("http://money.163.com/special/00252EQ2/caozuorss.xml");
        urls.add("http://money.163.com/special/00252EQ2/jjywrss.xml");
        urls.add("http://money.163.com/special/00252LIB/cjztrss.xml");
        urls.add("http://tech.163.com/digi/special/00161K7K/rss_digixj.xml");
        urls.add("http://tech.163.com/digi/special/00161K7K/rss_diginote.xml");
        urls.add("http://tech.163.com/digi/special/00161K7K/rss_digipc.xml");
        urls.add("http://mobile.163.com/special/001144R8/mobile163_copy.xml");
        urls.add("http://mobile.163.com/special/001144R8/xinjisudi_copy.xml");
        urls.add("http://mobile.163.com/special/001144R8/mirss.xml");
        urls.add("http://mobile.163.com/special/001144R8/mdrss.xml");
        urls.add("http://mobile.163.com/special/001144R8/shoujipingce.xml");
        urls.add("http://mobile.163.com/special/001144R8/shoujidaogou_copy.xml");
        urls.add("http://mobile.163.com/special/001144R8/phonemarketrss.xml");
        urls.add("http://bj.house.163.com/special/000741FI/bjrss.xml");
        urls.add("http://game.163.com/special/003144N4/rss_gametop.xml");
        urls.add("http://book.163.com/special/0092451H/rss_whzx.xml");
        urls.add("http://gongyi.163.com/special/009344MB/gyxw2.xml");
        urls.add("http://daxue.163.com/special/00913J5N/daxuerss.xml");
         //sina
        urls.add("http://blog.sina.com.cn/rss/THEBUND.xml");
        urls.add("http://blog.sina.com.cn/rss/sciam.xml");
        //凤凰网
        urls.add("http://news.ifeng.com/history/rss/index.xml");
        urls.add("http://news.ifeng.com/rss/society.xml");
        urls.add("http://zhihurss.jd-app.com/zhihuzhuanlan/taosay?limit=200");


    }

    @Test
    public void run() {
        Elements elements = document.getElementsByTag("item");
        List<Object[]> params = new ArrayList<Object[]>();

        List obj = null;
        for (Element em : elements) {
            obj = new ArrayList();
            obj.add(em.getElementsByTag("title").text());
            obj.add(em.getElementsByTag("description").text());
            obj.add(em.getElementsByTag("category").text());
            obj.add(em.getElementsByTag("author").text());
            // obj.add(format.format(em.getElementsByTag("pubDate").text().replaceAll(" +0800","")));
            obj.add(format.format(new Date(em.getElementsByTag("pubDate").text())));
            params.add(obj.toArray());
        }
        this.insert(params);
    }

    @Test
    public void runBatch() {
        for (String url : urls) {
            try {
                runDate(url);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("出错url====>" + url);
            }
        }
    }

    private void runDate(String url) throws IOException {
        connection = Jsoup.connect(url);
        document = connection.userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1 ").get();
        Elements elements = document.getElementsByTag("item");
        List<Object[]> params = new ArrayList<Object[]>();

        List obj = null;
        String pubDate = "";
        for (Element em : elements) {
            obj = new ArrayList();
            obj.add(em.getElementsByTag("title").text());
            obj.add(em.getElementsByTag("description").text());
            obj.add(em.getElementsByTag("category").text());
            obj.add(em.getElementsByTag("author").text());
            pubDate = em.getElementsByTag("pubDate").text();
            //pubDate.replaceAll("", "");
            pubDate.trim();
            if (null != pubDate && !"".equals(pubDate)) {
                if(pubDate.indexOf("\\+0800")==-1){
                    pubDate=pubDate.replaceAll("\\+0800","").trim();
                    obj.add(format.format(new Date(pubDate)));
                } else {
                    obj.add(format.format(new Date(pubDate)));
                }
            } else obj.add(format.format(new Date(System.currentTimeMillis())));
            params.add(obj.toArray());
        }
        this.insert(params);
    }

    private void insert(List<Object[]> list) {
        String sql = "INSERT INTO rss(title,description,category,author,pubDate)\n" +
                "VALUES(?,?,?,?,?)";

        executors.insertBatch(sql, list);

    }


    @Test
    public void runDate() {
        String str = "Mon, 01 Sep 2014 01:49:00 +0800";


        System.out.println("====>" + format.format(new Date(str)));
    }

    @AfterMethod
    public void close() {
        char s=(char)(true?1:0) ;
    }

}
