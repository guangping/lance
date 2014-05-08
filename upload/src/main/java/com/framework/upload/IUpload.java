package com.framework.upload;

import com.framework.upload.pojo.CommonResult;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 10:11
 * To change this template use File | Settings | File Templates.
 */
public interface IUpload {

    /*
    * 当前工作目录下
    * */
    public CommonResult upload(File file);

    /*
    *上传创建的目录下
    * **/
    public CommonResult upload(File file,String path);

    /*
    *创建多级目录,在上传文件
    * */
    public CommonResult upload(File file,List<String> dirs);


    /*
    *获取目录下的文件列表
    * */
    public List<String> getFileList(String path);


}
