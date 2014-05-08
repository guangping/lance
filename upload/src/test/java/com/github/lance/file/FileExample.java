package com.github.lance.file;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 18:27
 * To change this template use File | Settings | File Templates.
 */
public class FileExample {

    public static void main(String[] args) throws Exception {
        File directory = new File("F:\\worspace-sourcecode\\sss");
        // 轮询间隔 60 秒
        long interval = TimeUnit.SECONDS.toMillis(30);
        // 创建一个文件观察器用于处理文件的格式
        FileAlterationObserver observer = new FileAlterationObserver(directory,
                FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
                        FileFilterUtils.suffixFileFilter(".txt")));
        //设置文件变化监听器
        //observer.addListener(new FileListener());
        observer.addListener(new FileAdaptorListener());
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        monitor.start();
        //Thread.sleep(30000);
        //monitor.stop();
    }
}

final class FileAdaptorListener extends FileAlterationListenerAdaptor{
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    @Override
    public void onStart(FileAlterationObserver observer) {
        System.out.println(format.format(new Date(System.currentTimeMillis()))+": monitor start scan files..");
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        System.out.println(format.format(new Date(System.currentTimeMillis()))+": monitor stop scanning..");
    }

    @Override
    public void onFileCreate(File file) {
        System.out.println("[新建]:" + file.getAbsolutePath());
    }

}

//FileAlterationListenerAdaptor ，FileAlterationListener
final class FileListener implements FileAlterationListener {
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
        System.out.println("monitor start scan files..");
    }


    @Override
    public void onDirectoryCreate(File file) {
        System.out.println(file.getName() + " director created.");
    }


    @Override
    public void onDirectoryChange(File file) {
        System.out.println(file.getName() + " director changed.");
    }


    @Override
    public void onDirectoryDelete(File file) {
        System.out.println(file.getName() + " director deleted.");
    }


    @Override
    public void onFileCreate(File file) {
        System.out.println(file.getAbsolutePath() + " created.");
    }


    @Override
    public void onFileChange(File file) {
        System.out.println(file.getName() + " changed.");
    }


    @Override
    public void onFileDelete(File file) {
        System.out.println(file.getName() + " deleted.");
    }


    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
        System.out.println("monitor stop scanning..");
    }
}


