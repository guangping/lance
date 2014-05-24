package com.framework.cache.config;


public class CacheConfig {

    public static String DEFAULT_CACHE_TYPE="default";

    public static String DEFAULT_MEMCACHED_CACHE_TYPE="memcached";


    private String cahceType = "default";

    public String getCahceType() {
        return cahceType;
    }

    public void setCahceType(String cahceType) {
        this.cahceType = cahceType;
    }
}
