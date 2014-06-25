package com.framework.cache;


/**
 * Cache 工厂
 *
 * @author
 */
public class CacheFactory {

    private INetCache instance = DefaultCache.getInstance();

    public CacheFactory() {
    }


    public INetCache getDefaultCache() {
        return instance;
    }

    public void setInstance(INetCache instance) {
        this.instance = instance;
    }
}
