package com.framework.upload.utils;

import com.framework.upload.DefaultUploadConnection;
import com.framework.upload.IUpload;
import com.framework.upload.impl.FtpUploadConnImpl;
import com.framework.upload.impl.SFtpUploadConnImpl;
import com.framework.upload.pojo.CommonResult;
import com.framework.upload.pojo.ConnectionConfig;
import com.framework.upload.pojo.Protocol;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 10:32
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 文件上传辅助类
 */
public class UploadUtils {

    /*
    * 文件上传
    * */
    public static void upload(File file) {
        List<ConnectionConfig> list = DefaultUploadConnection.getConnectionConfig();
        if (null == list || list.isEmpty()) {
            throw new RuntimeException("Ftp或Sftp未配置.");
        }

        for (ConnectionConfig config : list) {
            if (null == config) continue;

            if (config.getProtocol().equals(Protocol.FTP)) {
                IUpload upload = new FtpUploadConnImpl(config);
                upload.upload(file);
                continue;
            }

            if (config.getProtocol().equals(Protocol.SFTP)) {
                IUpload upload = new SFtpUploadConnImpl(config);
                upload.upload(file);
            }
        }
    }

    /*
   * 文件上传
   * */
    public static void upload(File file, String path) {
        List<ConnectionConfig> list = DefaultUploadConnection.getConnectionConfig();
        if (null == list || list.isEmpty()) {
            throw new RuntimeException("Ftp或Sftp未配置.");
        }

        for (ConnectionConfig config : list) {
            if (null == config) continue;

            if (config.getProtocol().equals(Protocol.FTP)) {
                IUpload upload = new FtpUploadConnImpl(config);
                upload.upload(file, path);
                continue;
            }

            if (config.getProtocol().equals(Protocol.SFTP)) {
                IUpload upload = new SFtpUploadConnImpl(config);
                upload.upload(file, path);
            }
        }
    }

    /*
     * 文件上传
     * */
    public static void upload(File file, List<String> dirs) {
        List<ConnectionConfig> list = DefaultUploadConnection.getConnectionConfig();
        if (null == list || list.isEmpty()) {
            throw new RuntimeException("Ftp或Sftp未配置.");
        }

        for (ConnectionConfig config : list) {
            if (null == config) continue;

            if (config.getProtocol().equals(Protocol.FTP)) {
                IUpload upload = new FtpUploadConnImpl(config);
                upload.upload(file, dirs);
                continue;
            }

            if (config.getProtocol().equals(Protocol.SFTP)) {
                IUpload upload = new SFtpUploadConnImpl(config);
                upload.upload(file, dirs);
            }
        }
    }

    /*
    * 获取目录下的文件列表
    * **/
    public static List<String> getFileList(String path) {
        List<ConnectionConfig> list = DefaultUploadConnection.getConnectionConfig();
        if (null == list || list.isEmpty()) {
            throw new RuntimeException("Ftp或Sftp未配置.");
        }

        for (ConnectionConfig config : list) {
            if (null == config) continue;

            if (config.getProtocol().equals(Protocol.FTP)) {
                IUpload upload = new FtpUploadConnImpl(config);
                return upload.getFileList(path);
            }

            if (config.getProtocol().equals(Protocol.SFTP)) {
                IUpload upload = new SFtpUploadConnImpl(config);
                return upload.getFileList(path);
            }
        }

        return null;
    }


    public static CommonResult update(File file, ConnectionConfig config) {
        if (null == file || null == config) {
            throw new RuntimeException("Upload file or ConnectionConfig is null!");
        }

        if (config.getProtocol().equals(Protocol.FTP)) {
            IUpload upload = new FtpUploadConnImpl(config);
            return upload.upload(file);
        }

        if (config.getProtocol().equals(Protocol.SFTP)) {
            IUpload upload = new SFtpUploadConnImpl(config);
            return upload.upload(file);
        }
        return new CommonResult();
    }

    public static CommonResult update(File file, ConnectionConfig config,List<String> dirs) {
        if (null == file || null == config) {
            throw new RuntimeException("Upload file or ConnectionConfig is null!");
        }

        if (config.getProtocol().equals(Protocol.FTP)) {
            IUpload upload = new FtpUploadConnImpl(config);
            return upload.upload(file,dirs);
        }

        if (config.getProtocol().equals(Protocol.SFTP)) {
            IUpload upload = new SFtpUploadConnImpl(config);
            return upload.upload(file,dirs);
        }
        return new CommonResult();
    }
}
