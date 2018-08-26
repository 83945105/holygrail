package pub.avalon.holygrail.response.utils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;

/**
 * 资源文件读取工具
 *
 * @author 白超
 */
public class ResourceUtil implements Serializable {

    /**
     * @Fields serialVersionUID : 序列化标识符
     */
    private static final long serialVersionUID = -3889274686043327477L;

    /**
     * 系统语言环境-默认中文
     */
    public static final String LANGUAGE = "zh";
    /**
     * 系统国家换将-默认中国
     */
    public static final String COUNTRY = "CN";

    private static Locale getLocale() {
        return new Locale(LANGUAGE, COUNTRY);
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
            retValue = (String) rb.getObject(key);
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

        ArrayList<String> list = new ArrayList<>();
        Set<String> keySet = rb.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            list.add(it.next());
        }
        return list;
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
        HashMap<String, Object> rs = new HashMap<>(16);
        Set<String> keySet = rb.keySet();
        for (String key : keySet) {
            rs.put(MessageFormat.format(key, params), rb.getObject(key));
        }
        return rs;
    }
}
