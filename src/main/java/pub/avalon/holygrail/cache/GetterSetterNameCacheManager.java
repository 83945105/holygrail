package pub.avalon.holygrail.cache;

import pub.avalon.holygrail.utils.ClassUtil;
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
public class GetterSetterNameCacheManager {

    private static final String GETTER_METHOD_NAME_CACHE_ALIAS = "GetterMethodNameAccess";
    private static final String SETTER_METHOD_NAME_CACHE_ALIAS = "SetterMethodNameAccess";

    private static final Cache<String, String> GETTER_METHOD_NAME_ACCESS_CACHE;

    private static final Cache<String, String> SETTER_METHOD_NAME_ACCESS_CACHE;

    private GetterSetterNameCacheManager() {
    }

    static {
        CacheManager cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .withCache(GETTER_METHOD_NAME_CACHE_ALIAS,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                                ResourcePoolsBuilder.heap(100)).build())
                .withCache(SETTER_METHOD_NAME_CACHE_ALIAS,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                                ResourcePoolsBuilder.heap(100).build()))
                .build(true);
        GETTER_METHOD_NAME_ACCESS_CACHE = cacheManager.getCache(GETTER_METHOD_NAME_CACHE_ALIAS, String.class, String.class);
        SETTER_METHOD_NAME_ACCESS_CACHE = cacheManager.getCache(SETTER_METHOD_NAME_CACHE_ALIAS, String.class, String.class);
    }

    public static String getGetterMethodName(String propertyName, boolean isBoolean) {
        if (propertyName == null || propertyName.trim().length() == 0) {
            return null;
        }
        String name = GETTER_METHOD_NAME_ACCESS_CACHE.get(propertyName);
        if (name == null) {
            synchronized (GETTER_METHOD_NAME_ACCESS_CACHE) {
                name = ClassUtil.getGetterMethodName(propertyName, isBoolean);
                GETTER_METHOD_NAME_ACCESS_CACHE.put(propertyName, name);
            }
        }
        return name;
    }

    public static String getGetterMethodName(Class clazz, String propertyName) {
        PropertyInfo propertyInfo = ClassPropertyInfoCacheManager.getPropertyInfo(clazz, propertyName);
        if (propertyInfo == null) {
            return null;
        }
        String name = GETTER_METHOD_NAME_ACCESS_CACHE.get(propertyName);
        if (name == null) {
            synchronized (GETTER_METHOD_NAME_ACCESS_CACHE) {
                name = ClassUtil.getGetterMethodName(propertyName, propertyInfo.isBoolean());
                GETTER_METHOD_NAME_ACCESS_CACHE.put(propertyName, name);
            }
        }
        return name;
    }

    public static String getSetterMethodName(String propertyName) {
        if (propertyName == null) {
            return null;
        }
        String name = SETTER_METHOD_NAME_ACCESS_CACHE.get(propertyName);
        if (name == null) {
            synchronized (SETTER_METHOD_NAME_ACCESS_CACHE) {
                name = ClassUtil.getSetterMethodName(propertyName);
                SETTER_METHOD_NAME_ACCESS_CACHE.put(propertyName, name);
            }
        }
        return name;
    }

}
