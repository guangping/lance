package com.framework.cache;


import com.framework.cache.config.CacheConfig;
import com.framework.spring.SpringContextHolder;

/**
 * Cache 工厂
 * @author
 */
public class CacheFactory {

    private CacheFactory() {}

    public static INetCache getDefaultCache() {
        if (Boolean.valueOf(CacheConfig.get("uba.memcached.enable"))) {
            Object obj = SpringContextHolder.getBean("xmCache");
            if (null != obj) {
                return (INetCache) obj;
            }
            return null;
        }
        return DefaultCache.getInstance();
    }
}
