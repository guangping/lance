/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *dubbo服务启动
 */
package com.framework.dubbo.init;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Main. (API, Static, ThreadSafe)
 *
 * @author william.liangf
 */
public class Main {
    private static final String DUBBO_PROTOCOL_PORT="dubbo.protocol.port";
    private static final String DUBBO_SPRING_CONFIG="dubbo.spring.config";
    private static final String DUBBO_CONTAINER = "dubbo.container";
    private static final String DUBBO_REGISTRY_ADDRESS="dubbo.registry.address";

    public static final String SHUTDOWN_HOOK_KEY = "dubbo.shutdown.hook";

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final ExtensionLoader<Container> loader = ExtensionLoader.getExtensionLoader(Container.class);

    private static volatile boolean running = true;

    private static Properties ps=null;

    private static final String DUBBO_FILE="dubbo.properties";

    static {
        ps=new Properties();
        try{
            File file = new File(System.getProperty("CONFIG")+DUBBO_FILE);
            FileInputStream fileIS = new FileInputStream(file);
            ps.load(fileIS);

            //设置dubbo服务端口
            System.setProperty(DUBBO_PROTOCOL_PORT, ps.getProperty(DUBBO_PROTOCOL_PORT));
            System.setProperty(DUBBO_SPRING_CONFIG, ps.getProperty(DUBBO_SPRING_CONFIG));
            System.setProperty(DUBBO_CONTAINER,ps.getProperty(DUBBO_CONTAINER));
            System.setProperty(DUBBO_REGISTRY_ADDRESS,ps.getProperty(DUBBO_REGISTRY_ADDRESS));
            fileIS.close();
        }catch (IOException e){
            logger.debug("加载dubbo配置文件"+DUBBO_FILE+"出错!");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            if (args == null || args.length == 0) {
                String config = ConfigUtils.getProperty(DUBBO_CONTAINER, loader.getDefaultExtensionName());
                args = Constants.COMMA_SPLIT_PATTERN.split(config);
            }

            final List<Container> containers = new ArrayList<Container>();
            for (int i = 0; i < args.length; i ++) {
                containers.add(loader.getExtension(args[i]));
            }
            logger.info("Use container type(" + Arrays.toString(args) + ") to run dubbo serivce.");

            if ("true".equals(System.getProperty(SHUTDOWN_HOOK_KEY))) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        for (Container container : containers) {
                            try {
                                container.stop();
                                logger.info("Dubbo " + container.getClass().getSimpleName() + " stopped!");
                            } catch (Throwable t) {
                                logger.error(t.getMessage(), t);
                            }
                            synchronized (Main.class) {
                                running = false;
                                Main.class.notify();
                            }
                        }
                    }
                });
            }

            for (Container container : containers) {
                container.start();
                logger.info("Dubbo " + container.getClass().getSimpleName() + " started!");
            }
            System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Dubbo service server started!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            System.exit(1);
        }
        synchronized (Main.class) {
            while (running) {
                try {
                    Main.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }

}