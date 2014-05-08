package com.framework.upload.impl;

import com.framework.upload.IUpload;
import com.framework.upload.pojo.CommonResult;
import com.framework.upload.pojo.ConnectionConfig;
import com.framework.upload.utils.StringUtils;
import com.jcraft.jsch.*;
import org.slf4j.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 10:22
 * To change this template use File | Settings | File Templates.
 */
public class SFtpUploadConnImpl implements IUpload {
    private org.slf4j.Logger logger= LoggerFactory.getLogger(getClass());
    private ChannelSftp sftp;
    private ConnectionConfig config;

    public SFtpUploadConnImpl(ConnectionConfig config) {
        this.config = config;
        try {
            JSch jsch = new JSch();
            Session sshSession = jsch.getSession(config.getUser(), config.getHost(), config.getPort());
            sshSession.setPassword(config.getPassword());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            if(StringUtils.isNotBlank(config.getPath())){
                sftp.cd(config.getPath());
            }
        } catch (JSchException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Connected to {} fail.", config.getHost());
            }
            e.printStackTrace();
        } catch (SftpException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Sftp {} cd {} fail.", new Object[]{config.getHost(),config.getPath()});
            }
            e.printStackTrace();
        }
    }

    private void close() {
        if (null != sftp && sftp.isConnected()) {
            sftp.disconnect();
        }
    }

    @Override
    public CommonResult upload(File file) {
        CommonResult result = new CommonResult();
        result.setHost(config.getHost());
        try {
            sftp.put(new FileInputStream(file), file.getName());
            result.setResult(true);
            result.setMsg("success");
        } catch (FileNotFoundException e) {
            if(logger.isDebugEnabled()){
                logger.debug("上传文件不存在.{}",e.getMessage());
            }
            e.printStackTrace();
        } catch (SftpException e) {
            if(logger.isDebugEnabled()){
                logger.debug("Sftp上传文件出错.",e.getMessage());
            }
            e.printStackTrace();
        } finally {
            this.close();
            return result;
        }
    }

    @Override
    public CommonResult upload(File file, String path) {
        CommonResult result=new CommonResult();
        result.setHost(config.getHost());
        result.setMsg("文件上传失败!目录"+path);
        if(this.createdirs(path)){
           result=this.upload(file);
        }
        return result;
    }

    /*
    * 创建目录
    * */
    private boolean createdirs(String path){
        boolean result=false;
        try {
            sftp.cd(path);
            result=true;
        } catch (SftpException e) {
            //e.printStackTrace();
        }
        if(!result){
            try {
                sftp.mkdir(path);
                sftp.cd(path);
                result=true;
            } catch (SftpException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public CommonResult upload(File file, List<String> dirs) {
        CommonResult result=new CommonResult();
        result.setHost(config.getHost());
        StringBuffer str=new StringBuffer(100);
        boolean val=true;
        for(String obj : dirs){
            str.append(obj);
            str.append("/");
            if(StringUtils.isBlank(obj)){
                val=false;
                break;
            }

            if(!this.createdirs(obj)){
                val=false;
                break;
            }
        }
        result.setMsg("文件上传失败!目录"+str.toString());
        if(val){
            result=upload(file);
        }
        return result;
    }

    @Override
    public List<String> getFileList(String path) {
        List<String> list=new ArrayList<String>();
        try {
            if(StringUtils.isBlank(path)){
               path="./";
            }
            Vector vector=sftp.ls(path);
            ChannelSftp.LsEntry entry=null;
            for(Object obj : vector){
                entry=(ChannelSftp.LsEntry)obj;
                list.add(entry.getFilename());
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }finally {
            this.close();
            return list;
        }
    }
}
