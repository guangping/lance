package com.framework.cache.config;


import com.framework.utils.StringUtils;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;


public class Config {
	private static final String CONF_NAME="CacheConfig.properties" ;
	private static Properties properties ;
	static{
		properties = getConfigFile()  ;
	}
	
	
	public static String get(String key){
		return properties.getProperty(key) ;
	}
	
	private Config(){
		
	}
	
	public  static Properties getConfigFileProperties(String fileName) {
		InputStream is;
		Properties configFile  = new Properties() ;
		try {
			is = getFileInputStream( fileName);
			configFile.load(is) ;
			is.close() ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigException(e) ;
		}
		
		return configFile ;
	}
	

	private static InputStream getFileInputStream(String fileName) throws Exception {
		return Config.class.getClassLoader().getResourceAsStream(fileName) ;
	}
	
	public  static Properties getConfigFile()  {
        String dir=System.getProperty("CACHE_PATH");
        if(StringUtils.isNotBlank(dir)){
            dir=dir+"/"+CONF_NAME;
        } else {
            dir=CONF_NAME;
        }
		return getConfigFileProperties(dir) ;
	}
	
	public static void main(String[] args){
		Properties p = getConfigFile() ;
		int i = 0 ; 
	}
	

	public static void writeBackWhileAPPExit() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					FileWriter fw = new FileWriter("d:\\t.log");
					fw.write("the application ended! "
							+ (new Date()).toString());
					fw.close();
				} catch (IOException ex) {

				}
			}
		});
	}
}
