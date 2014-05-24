package com.framework.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//默认实现
public class DefaultCache implements INetCache {
	private static Map cacheData = new ConcurrentHashMap() ;


	private DefaultCache(){}
    private static class MockCacheClassLoader{
        private static DefaultCache instance = new DefaultCache();
    }
	
	public static DefaultCache getInstance(){
		return MockCacheClassLoader.instance ;
	}


	@Override
	public void delete(String key) {
		cacheData.remove(key) ;
	}

	@Override
	public void deleteWithNoReply(String key) {
		cacheData.remove(key) ;
	}

	@Override
	public void flush() {
		cacheData.clear() ;
	}

	@Override
	public IBean getBean(String key) {
		return (IBean)cacheData.get(key);
	}

	@Override
	public Serializable getObj(String key) {
		return (Serializable)cacheData.get(key);
	}

	@Override
	public void setBean(String key, int cacheTime, IBean value) {
		cacheData.put(key, value) ;
	}

	@Override
	public void setObj(String key, int cacheTime, Serializable value) {
		cacheData.put(key, value) ;
	}

    @Override
    public void update(String key, int time, Serializable value) {
        cacheData.put(key, value) ;
    }

}
