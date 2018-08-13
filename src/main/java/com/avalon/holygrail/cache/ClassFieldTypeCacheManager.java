package com.avalon.holygrail.cache;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * 类属性类型缓存
 *
 * @author 白超
 * @date 2018/8/13
 */
public class ClassFieldTypeCacheManager {

    private static final String FIELD_TYPE_CACHE_ALIAS = "FieldType";

    private static Cache<String, String> FIELD_ACCESS_CACHE;


    private ClassFieldTypeCacheManager() {
    }

    static {
        CacheManager cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .withCache(FIELD_TYPE_CACHE_ALIAS,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                                ResourcePoolsBuilder.heap(100).build()))
                .build(true);
    }

}
