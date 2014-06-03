package com.ztesoft.inf.service.util;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 读取spring  xml文件
 * @author hzcl-sky
 *
 */
public class XmlApplicationContext {
	private ClassPathXmlApplicationContext cxa;

	public XmlApplicationContext(String path) {
		cxa = new ClassPathXmlApplicationContext(path);
	}
	public XmlApplicationContext(String[] configLocations) {
		cxa = new ClassPathXmlApplicationContext(configLocations);
	}
	public XmlApplicationContext(String[] configLocations, ApplicationContext parent){
		cxa=new ClassPathXmlApplicationContext(configLocations,parent);
	}
	public XmlApplicationContext(String [] path,boolean refresh){
		cxa=new ClassPathXmlApplicationContext(path,refresh);
	}
	public XmlApplicationContext(String [] path,boolean refresh,ApplicationContext parent){
		cxa=new ClassPathXmlApplicationContext(path,refresh,parent);
	}
	public ClassPathXmlApplicationContext getClassPathXmlApplicationContext(){
		return cxa; 
	}
	
	public Object getBean(String name){
		return (Object) cxa.getBean(name);
	}
	public Object getBean(String name,Class clazz){
		return (Object)cxa.getBean(name, clazz);
	}
	public ConfigurableListableBeanFactory getBeanFactory(){
		return cxa.getBeanFactory();
	}
	public boolean containsBean(String name){
		return cxa.containsBean(name);
	}
	public boolean containsBeanDefinition(String name){
		return cxa.containsBeanDefinition(name);
	}
	public boolean isSingleton(String name){
		return cxa.isSingleton(name);
	}
}

