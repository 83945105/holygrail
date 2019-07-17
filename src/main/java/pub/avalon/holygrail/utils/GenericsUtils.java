package pub.avalon.holygrail.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具
 *
 * @author 白超
 * @date 2019/7/17
 */
public class GenericsUtils {

    private GenericsUtils() {
    }

    /**
     * 获取定义Class时声明的父类的泛型参数的类型
     *
     * @param clazz
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * 从祖先类型中获取期望的泛型参数类型
     *
     * @param clazz
     * @param expectClass
     * @return
     */
    public static Class getExpectAncestorsClassGenricType(Class clazz, Class<?> expectClass) {
        if (clazz == Object.class) {
            return null;
        }
        Type genType = clazz.getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            for (Type param : params) {
                if (param instanceof Class) {
                    if (expectClass.isAssignableFrom((Class<?>) param)) {
                        return (Class) param;
                    }
                }
            }

        }
        return getExpectAncestorsClassGenricType(clazz.getSuperclass(), expectClass);
    }

}
