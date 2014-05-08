package com.framework.file.discover;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 20:42
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 检测文件变化入口类
 */
public class Main {
    private static final String FILE_TYPE = "file.type";
    private static final String POLL_TIME = "poll.time";
    private static final String FILE_DIR = "file.dir";
    private static final String FTP_CONFIG="ftp.config";
    private static final String FILE_PROPERTIES = "connconfig.properties";
    private static Properties properties = null;

    static {
        InputStream inputStream = null;
        try {
            inputStream = Main.class.getClassLoader().getResourceAsStream(FILE_PROPERTIES);
            properties = new Properties();
            properties.load(inputStream);
            System.setProperty(FILE_DIR,properties.getProperty(FILE_DIR));
            System.setProperty(FTP_CONFIG,properties.getProperty(FTP_CONFIG));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static void main(String[] args) throws Exception {
        try{
            String file_type = properties.getProperty(FILE_TYPE);
            String poll_time = properties.getProperty(POLL_TIME);
            String file_dir = properties.getProperty(FILE_DIR);

            File directory = new File(file_dir);
            List<String> list = Arrays.asList(file_type.split("\\,"));
            List<IOFileFilter> ioFileFilters = new ArrayList<IOFileFilter>();
            for (String obj : list) {
                ioFileFilters.add(FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(obj)));
            }
            // 轮询间隔 秒
            long interval = TimeUnit.SECONDS.toMillis(Integer.parseInt(poll_time));
            // 创建一个文件观察器用于处理文件的格式
            IOFileFilter fileFilter = FileFilterUtils.or(ioFileFilters.toArray(new IOFileFilter[]{}));
            FileAlterationObserver observer = new FileAlterationObserver(directory, fileFilter);
            observer.setIoFileFilter(fileFilter);
            //设置文件变化监听器
            observer.addListener(new FileListener());

            FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
            monitor.start();
            //Thread.sleep(30000);
            //monitor.stop();
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }
}
