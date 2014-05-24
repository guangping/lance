package com.framework.cache;


import com.framework.cache.config.CacheConfig;
import com.framework.spring.SpringContextHolder;

/**
 * Cache 工厂
 *
 * @author
 */
public class CacheFactory {

    private CacheFactory() {
    }


    public static INetCache getDefaultCache() {
        CacheConfig config = SpringContextHolder.getBean("cacheConfig");
        if (null != config) {
            if (config.getCahceType().equals(CacheConfig.DEFAULT_CACHE_TYPE)) {
                return DefaultCache.getInstance();
            }
            if (config.getCahceType().endsWith(CacheConfig.DEFAULT_MEMCACHED_CACHE_TYPE)) {
                INetCache netCache = SpringContextHolder.getBean("xmCache");
                return netCache;
            }
        }
        return null;
    }
}
