package com.github.lance.upload;

import com.framework.upload.pojo.CommonResult;
import com.framework.upload.pojo.ConnectionConfig;
import com.framework.upload.pojo.Protocol;
import com.framework.upload.utils.UploadUtils;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-12 10:42
 * To change this template use File | Settings | File Templates.
 */
public class UploadTest {

    @Test
    public void upload() {
        ConnectionConfig config = new ConnectionConfig();
        config.setHost("10.45.47.170");
        config.setPort(22);
        config.setUser("root");
        config.setPassword("super242");
        config.setProtocol(Protocol.SFTP);
        config.setPath("/home");

        File file=new File("C:\\Users\\guangping\\Desktop\\nginx.conf");

        CommonResult result= UploadUtils.update(file,config);
        System.out.println("上传结果:"+result.isResult());
    }
}
