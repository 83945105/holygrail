package pub.avalon.holygrail.response.utils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

/**
 * 资源文件读取工具
 *
 * @author 白超
 */
public class ResourceUtil implements Serializable {

    private static final long serialVersionUID = -3889274686043327477L;

    private ResourceUtil() {
    }

    /**
     * 系统语言环境-默认中文
     */
    private static final String LANGUAGE = "zh";
    /**
     * 系统国家换将-默认中国
     */
    private static final String COUNTRY = "CN";

    private static final Locale LOCAL = new Locale(LANGUAGE, COUNTRY);

    private static Locale getLocale() {
        return LOCAL;
    }

    /**
     * 根据语言、国家、资源文件名、key名字获取资源文件值
     *
     * @param fileName 资源文件名
     * @param key      key名
     */
    public static String getValue(String fileName, String key) {
        String retValue = "";
        try {
            Locale locale = getLocale();
            ResourceBundle rb = ResourceBundle.getBundle(fileName, locale);
            retValue = new String(rb.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 获取Key值集合
     *
     * @param fileName
     * @return
     */
    public static ArrayList<String> getKeyArrayList(String fileName) {
        Locale locale = getLocale();
        ResourceBundle rb = ResourceBundle.getBundle(fileName, locale);
        return new ArrayList<>(rb.keySet());
    }

    /**
     * 通过key从资源文件读取内容并格式化
     *
     * @param fileName 资源文件名
     * @param key      索引
     * @param params   格式化参数
     */
    public static String getValue(String fileName, String key, Object[] params) {
        String pattern = getValue(fileName, key);
        return MessageFormat.format(pattern, params);
    }

    /**
     * 获取所有key-value
     *
     * @param fileName
     * @param params
     * @return
     */
    public static HashMap<String, Object> getKeyValues(String fileName, Object[] params) {
        Locale locale = getLocale();
        ResourceBundle rb = ResourceBundle.getBundle(fileName, locale);
        Set<String> keySet = rb.keySet();
        HashMap<String, Object> rs = new HashMap<>(keySet.size());
        for (String key : keySet) {
            rs.put(MessageFormat.format(key, params), rb.getObject(key));
        }
        return rs;
    }
}
