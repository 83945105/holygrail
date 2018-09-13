package pub.avalon.holygrail.utils;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import pub.avalon.holygrail.cache.AsmAccessCacheManager;
import pub.avalon.holygrail.cache.ClassPropertyInfoCacheManager;
import pub.avalon.holygrail.cache.PropertyInfo;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * @author 白超
 * @date 2018-1-2
 */
public class ClassUtil {

    private ClassUtil() {
    }

    /**
     * 获取所有Field名称
     *
     * @param clazz
     * @return
     */
    public static ArrayList<String> getAllFieldNames(Class<?> clazz) {
        ArrayList<String> rs = new ArrayList<>();
        for (Class<?> cla = clazz; cla != Object.class; cla = cla.getSuperclass()) {
            for (Field field : cla.getDeclaredFields()) {
                rs.add(field.getName());
            }
        }
        return rs;
    }

    /**
     * 获取所有Field
     *
     * @param clazz
     * @return
     */
    public static ArrayList<Field> getAllFields(Class<?> clazz) {
        ArrayList<Field> rs = new ArrayList<>();
        for (Class<?> cla = clazz; cla != Object.class; cla = cla.getSuperclass()) {
            for (Field field : cla.getDeclaredFields()) {
                rs.add(field);
            }
        }
        return rs;
    }

    /**
     * 获取所有Method
     *
     * @param clazz
     * @return
     */
    public static ArrayList<Method> getAllMethods(Class<?> clazz) {
        ArrayList<Method> rs = new ArrayList<>();

        for (Class<?> cla = clazz; cla != Object.class; cla = cla.getSuperclass()) {
            rs.addAll(Arrays.asList(cla.getDeclaredMethods()));
        }
        return rs;
    }

    /**
     * 获取对象指定方法
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static Method getMethod(Class<?> clazz, String methodName) {
        for (Class<?> cla = clazz; cla != Object.class; cla = cla.getSuperclass()) {
            for (Method method : cla.getDeclaredMethods()) {
                if (methodName.equals(method.getName())) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 根据属性名称和java类型，获取对应的getter方法名
     *
     * @param propertyName 属性名称
     * @param isBoolean    是否是布尔类型
     * @return
     */
    public static String getGetterMethodName(String propertyName, boolean isBoolean) {
        if (propertyName == null || "".equals(propertyName.trim())) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(propertyName);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }
        if (isBoolean) {
            sb.insert(0, "is");
        } else {
            sb.insert(0, "get");
        }
        return sb.toString();
    }

    /**
     * 根据属性名称获取对应的setter方法名称
     *
     * @param propertyName 属性名称
     * @return
     */
    public static String getSetterMethodName(String propertyName) {
        if (propertyName == null || "".equals(propertyName.trim())) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(propertyName);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }
        sb.insert(0, "set");
        return sb.toString();
    }

    /**
     * 获取属性
     *
     * @param javaBean     对象
     * @param propertyName 属性名
     * @return
     */
    public static Object getProperty(Object javaBean, String propertyName) {
        if (javaBean instanceof Map) {
            return ((Map) javaBean).get(propertyName);
        }
        Class clazz = javaBean.getClass();
        MethodAccess methodAccess = AsmAccessCacheManager.getMethodAccess(clazz);
        PropertyInfo propertyInfo = ClassPropertyInfoCacheManager.getPropertyInfo(clazz, propertyName);
        if (propertyInfo == null) {
            return null;
        }
        return methodAccess.invoke(javaBean, propertyInfo.getGetterMethodName());
    }

    /**
     * 设置属性
     *
     * @param javaBean     对象
     * @param propertyName 属性名
     * @param value        属性值
     */
    @SuppressWarnings("unchecked")
    public static void setProperty(Object javaBean, String propertyName, Object value) {
        if (javaBean instanceof Map) {
            ((Map) javaBean).put(propertyName, value);
            return;
        }
        if (value == null) {
            return;
        }
        Class clazz = javaBean.getClass();
        MethodAccess methodAccess = AsmAccessCacheManager.getMethodAccess(clazz);
        PropertyInfo propertyInfo = ClassPropertyInfoCacheManager.getPropertyInfo(clazz, propertyName);
        if (propertyInfo == null) {
            return;
        }
        if (value.getClass() == propertyInfo.getType()) {
            methodAccess.invoke(javaBean, propertyInfo.getSetterMethodName(), value);
            return;
        }
        methodAccess.invoke(javaBean, propertyInfo.getSetterMethodName(), TypeUtils.cast(value, propertyInfo.getType(), ParserConfig.getGlobalInstance()));
    }

}
