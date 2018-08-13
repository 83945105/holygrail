package com.avalon.holygrail.cache;

import com.avalon.holygrail.utils.ClassUtil;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 类属性类型缓存
 *
 * @author 白超
 * @date 2018/8/13
 */
public class ClassPropertyInfoCacheManager {

    private static final String CLASS_PROPERTY_INFO_CACHE_ALIAS = "FieldType";

    private static final Cache<Class, NamePropertyInfoMap> CLASS_PROPERTY_INFO_CACHE;

    private ClassPropertyInfoCacheManager() {
    }

    static {
        CacheManager cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .withCache(CLASS_PROPERTY_INFO_CACHE_ALIAS,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Class.class, NamePropertyInfoMap.class,
                                ResourcePoolsBuilder.heap(100).build()))
                .build(true);
        CLASS_PROPERTY_INFO_CACHE = cacheManager.getCache(CLASS_PROPERTY_INFO_CACHE_ALIAS, Class.class, NamePropertyInfoMap.class);
    }

    public static PropertyInfo getPropertyInfo(Class clazz, String propertyName) {
        if (clazz == null || propertyName == null || propertyName.trim().length() == 0) {
            return null;
        }
        NamePropertyInfoMap namePropertyInfoMap = CLASS_PROPERTY_INFO_CACHE.get(clazz);
        if (namePropertyInfoMap == null) {
            synchronized (CLASS_PROPERTY_INFO_CACHE) {
                namePropertyInfoMap = new NamePropertyInfoMap();
                for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        namePropertyInfoMap.put(field.getName(), initPropertyInfo(clazz, field));
                    }
                }
                CLASS_PROPERTY_INFO_CACHE.put(clazz, namePropertyInfoMap);
            }
        }
        return namePropertyInfoMap.get(propertyName);
    }

    private static PropertyInfo initPropertyInfo(Class clazz, Field field) {
        PropertyInfo propertyInfo = new PropertyInfo();
        String name = field.getName();
        propertyInfo.setName(name);
        propertyInfo.setField(field);
        propertyInfo.setType(field.getType());
        boolean isBoolean = field.getType() == boolean.class;
        propertyInfo.setBoolean(isBoolean);
        String getterMethodName = ClassUtil.getGetterMethodName(name, isBoolean);
        propertyInfo.setGetterMethodName(getterMethodName);
        String setterMethodName = ClassUtil.getSetterMethodName(name);
        propertyInfo.setSetterMethodName(setterMethodName);
        return propertyInfo;
    }

    private final static class NamePropertyInfoMap extends HashMap<String, PropertyInfo> {

    }

}
