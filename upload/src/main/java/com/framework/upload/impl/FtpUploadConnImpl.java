package com.framework.upload.impl;

import com.framework.upload.IUpload;
import com.framework.upload.pojo.CommonResult;
import com.framework.upload.pojo.ConnectionConfig;
import com.framework.upload.utils.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 10:22
 * To change this template use File | Settings | File Templates.
 */
public class FtpUploadConnImpl implements IUpload {
    private Logger logger= LoggerFactory.getLogger(getClass());

    private FTPClient client = null;
    private ConnectionConfig config=null;

    public FtpUploadConnImpl(ConnectionConfig config) {
        this.config=config;
        client = new FTPClient();
        try {
            client.setConnectTimeout(1800000);// 设置超时时间
            client.connect(config.getHost(), config.getPort());// 连接ftp
            client.login(config.getUser(), config.getPassword());// 登录ftp
            if (FTPReply.isPositiveCompletion(client.getReplyCode())) {
                // 设置编码，避免中文乱码
                client.setControlEncoding("GBK");
                client.setBufferSize(2048);
                if(null!=config.getPath() && !"".equals(config.getPath())){
                    client.changeWorkingDirectory(config.getPath());
                }
                FTPClientConfig ftpClientConfig = new FTPClientConfig(FTPClientConfig.SYST_NT);
                ftpClientConfig.setServerLanguageCode("zh");
                client.setFileType(FTPClient.BINARY_FILE_TYPE);//设置2进制传输

            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("未连接到FTP，用户名或密码错误。");
                }
            }
        } catch (SocketException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("FTP的IP地址可能错误，请正确配置.{}", e.getMessage());
            }
            e.printStackTrace();
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("FTP的端口错误,请正确配置.{}", e.getMessage());
            }
            e.printStackTrace();
        }
    }


    public CommonResult upload(File file) {
        CommonResult result = new CommonResult();
        boolean val = false;
        try {
            if (null != client && client.isAvailable()) {
                val = client.storeFile(file.getName(), new FileInputStream(file));
                result.setResult(val);
                result.setMsg("success");
                result.setHost(config.getHost());
            }
        } catch (IOException e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        } finally {
            this.close();
            return result;
        }
    }

    private void close() {
        if (null != client && client.isConnected()) {
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*
    *文件上传,可指定上传路径
    * */
    @Override
    public CommonResult upload(File file, String path) {
        CommonResult result=new CommonResult();
        boolean val=this.createDirs(path);
        if(val){
           result=this.upload(file);
        }
        return result;
    }

    /*
    *创建目录
    * */
    private boolean createDirs(String path){
        boolean val=false;
        try {
           if(client.changeWorkingDirectory(path)){
               val=true;
               return val;
           }
           val=client.makeDirectory(path);
           if(val){
               client.changeWorkingDirectory(path);
           }
           if(logger.isDebugEnabled()){
               logger.debug("文件夹创建结果:{}",val);
           }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return val;
        }
    }

    @Override
    public CommonResult upload(File file, List<String> dirs) {
        boolean val=true;
        CommonResult result=new CommonResult();
        for(String obj : dirs){
            if(StringUtils.isBlank(obj)){
                val=false;
                break;
            }

            if(!this.createDirs(obj)){
                val=false;
                break;
            }
        }
        result.setMsg("文件夹创建失败.");
        if(val){
            result=upload(file);
        }
        return result;
    }

    @Override
    public List<String> getFileList(String path) {
        List<String> list = null;
        try {
            FTPFile files[] = null;
            if (StringUtils.isNotBlank(path)) {
                files = client.listFiles(path);
            } else {
                files = client.listFiles();
            }
            for(FTPFile file:files){
                list.add(file.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.close();
            return list;
        }
    }
}
