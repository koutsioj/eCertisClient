package com.github.koutsioj;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.time.Duration;

public class CacheHelper {

    private CacheManager cacheManager;

    public CacheHelper() {
        cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder().build();
        cacheManager.init();
    }

    public Cache<String, String> createHeapCache(String alias,int heapEntries,int ExpirationTimeMinutes) {

        Cache<String, String> cache;
        cache = cacheManager
                .createCache(alias, CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                String.class, String.class, ResourcePoolsBuilder.heap(heapEntries))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(ExpirationTimeMinutes))));

        return cache;
    }

    public Cache<String , String> getCache(String alias) {
        Cache<String, String> cache =
                cacheManager.getCache(alias, String.class, String.class);

        return cache;
    }
}