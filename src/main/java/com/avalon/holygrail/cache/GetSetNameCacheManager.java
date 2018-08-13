package com.avalon.holygrail.cache;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * 缓存get/set方法名
 *
 * @author 白超
 * @date 2018/8/13
 */
public class GetSetNameCacheManager {

    private static final String GETTER_METHOD_NAME_CACHE_ALIAS = "GetterMethodNameAccess";
    private static final String SETTER_METHOD_NAME_CACHE_ALIAS = "SetterMethodNameAccess";

    private static final Cache<String, String> GETTER_METHOD_NAME_ACCESS_CACHE;

    private static final Cache<String, String> SETTER_METHOD_NAME_ACCESS_CACHE;

    private GetSetNameCacheManager() {
    }

    static {
        CacheManager cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .withCache(GETTER_METHOD_NAME_CACHE_ALIAS,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Class.class, FieldAccess.class,
                                ResourcePoolsBuilder.heap(100)).build())
                .withCache(SETTER_METHOD_NAME_CACHE_ALIAS,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Class.class, MethodAccess.class,
                                ResourcePoolsBuilder.heap(100).build()))
                .build(true);
        GETTER_METHOD_NAME_ACCESS_CACHE = cacheManager.getCache(GETTER_METHOD_NAME_CACHE_ALIAS, String.class, String.class);
        SETTER_METHOD_NAME_ACCESS_CACHE = cacheManager.getCache(SETTER_METHOD_NAME_CACHE_ALIAS, String.class, String.class);
    }

    public static String getGetterMethodName(String property, String javaType) {



        return null;
    }
}
