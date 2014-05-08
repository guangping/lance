package com.framework.file.discover;

import com.framework.upload.utils.StringUtils;
import com.framework.upload.utils.UploadUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 20:42
 * To change this template use File | Settings | File Templates.
 *
 *文件变化监听器
 *实现 FileAlterationListener 或继承 FileAlterationListenerAdaptor
 */
public class FileListener extends FileAlterationListenerAdaptor {
    Logger logger= LoggerFactory.getLogger(getClass());

    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    @Override
    public void onStart(FileAlterationObserver observer) {
        if(logger.isInfoEnabled()){
           logger.info("{} monitor start scan files.",format.format(new Date(System.currentTimeMillis())));
        }
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        if(logger.isInfoEnabled()){
            logger.info("{}  monitor stop scanning.",format.format(new Date(System.currentTimeMillis())));
        }
    }

    @Override
    public void onFileCreate(File file) {
        if(logger.isDebugEnabled()){
            logger.debug("同步文件:{}",file.getAbsoluteFile());
        }
        String file_dir=System.getProperty("file.dir");
        String abs_dir=file.getAbsolutePath();
        String dir=abs_dir.substring(file_dir.length(), abs_dir.length() - file.getName().length());
        List<String> list= Arrays.asList(dir.split("\\"+ File.separator));
        List<String> dirs=new ArrayList<String>();
        for(String obj:list){
            if(StringUtils.isNotBlank(obj)){
                dirs.add(obj);
            }
        }
        UploadUtils.upload(file,dirs);
    }

}
