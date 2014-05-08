package com.github.lance;

import com.framework.upload.pojo.CommonResult;
import com.framework.upload.utils.UploadUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.testng.annotations.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 11:35
 * To change this template use File | Settings | File Templates.
 */
public class MainTest {

    @Test
    public void upload() {
        File file = new File("F:\\jdbc\\settings.jar");
        UploadUtils.upload(file);
        //System.out.printf("文件上传结果:"+result.isResult()+",上传的服务器:"+result.getHost());
    }

    @Test
    public void upload2() {
        File file = new File("F:\\jdbc\\settings.jar");
        UploadUtils.upload(file,"2014");
        //System.out.printf("文件上传结果:"+result.isResult()+",上传的服务器:"+result.getHost());
    }

    @Test
    public void upload3() {
        File file = new File("F:\\jdbc\\settings.jar");
        List<String> list=new ArrayList<String>();
        list.add("3013");
        list.add("13");
        UploadUtils.upload(file,list);
        //System.out.printf("文件上传结果:"+result.isResult()+",上传的服务器:"+result.getHost());
    }


    @Test
    public void getlist() {
        List<String> list = UploadUtils.getFileList("");
        for(String ftpFile : list){
            System.out.println("fileName:"+ftpFile);
        }
    }

    @Test
    public void lastModified(){
        File file = new File("F:\\worspace-sourcecode\\tes.txt");

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.err.println("文件操作时间:"+ format.format(new Date(file.lastModified())));
    }

    @Test
    public void file(){
        File file = new File("F:\\worspace-sourcecode");

        FileUtils.isFileNewer(file,new Date());
        Collection<File> collection=FileUtils.listFiles(file, new String[]{"txt", "sql"}, true);
        for(File obj : collection){
            System.out.println(obj.getAbsolutePath());
        }
    }

    @Test
    public void fileFilter(){
        File file = new File("F:\\worspace-sourcecode\\sss");
        IOFileFilter fileFilter=FileFilterUtils.or(FileFilterUtils.and(FileFilterUtils.fileFileFilter(),FileFilterUtils.suffixFileFilter(".sql")),
                FileFilterUtils.and(FileFilterUtils.fileFileFilter(),FileFilterUtils.suffixFileFilter(".txt")));
        Collection<File> collection= FileUtils.listFiles(file,
                fileFilter,
                FileFilterUtils.and(FileFilterUtils.directoryFileFilter()));

        for(File obj : collection){
            System.out.println(obj.getAbsolutePath());
        }
        /*FileUtils.isFileNewer(file,new Date());
        Collection<File> collection=FileUtils.listFiles(file, new String[]{"txt", "sql"}, true);
        for(File obj : collection){
            System.out.println(obj.getAbsolutePath());
        }*/
    }

    @Test
    public void strSplit(){
        String s="33";
        String items[]=s.split("\\/");
        System.out.println(items.length);
    }
}
