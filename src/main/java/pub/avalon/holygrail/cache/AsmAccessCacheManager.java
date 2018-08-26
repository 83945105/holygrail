package pub.avalon.holygrail.cache;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * ASM缓存
 *
 * @author 白超
 * @date 2018/8/13
 */
public class AsmAccessCacheManager {

    private static final String FIELD_CACHE_ALIAS = "FieldAccess";
    private static final String METHOD_CACHE_ALIAS = "MethodAccess";

    private static final Cache<Class, FieldAccess> FIELD_ACCESS_CACHE;

    private static final Cache<Class, MethodAccess> METHOD_ACCESS_CACHE;

    private AsmAccessCacheManager() {
    }

    static {
        CacheManager cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .withCache(FIELD_CACHE_ALIAS,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Class.class, FieldAccess.class,
                                ResourcePoolsBuilder.heap(100)).build())
                .withCache(METHOD_CACHE_ALIAS,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Class.class, MethodAccess.class,
                                ResourcePoolsBuilder.heap(100).build()))
                .build(true);
        FIELD_ACCESS_CACHE = cacheManager.getCache(FIELD_CACHE_ALIAS, Class.class, FieldAccess.class);
        METHOD_ACCESS_CACHE = cacheManager.getCache(METHOD_CACHE_ALIAS, Class.class, MethodAccess.class);
    }

    public static FieldAccess getFieldAccess(Class clazz) {
        if (clazz == null) {
            return null;
        }
        FieldAccess fieldAccess = FIELD_ACCESS_CACHE.get(clazz);
        if (fieldAccess == null) {
            synchronized (FIELD_ACCESS_CACHE) {
                fieldAccess = FieldAccess.get(clazz);
                FIELD_ACCESS_CACHE.put(clazz, fieldAccess);
            }
        }
        return fieldAccess;
    }

    public static MethodAccess getMethodAccess(Class clazz) {
        if (clazz == null) {
            return null;
        }
        MethodAccess methodAccess = METHOD_ACCESS_CACHE.get(clazz);
        if (methodAccess == null) {
            synchronized (METHOD_ACCESS_CACHE) {
                methodAccess = MethodAccess.get(clazz);
                METHOD_ACCESS_CACHE.put(clazz, methodAccess);
            }
        }
        return methodAccess;
    }

}
