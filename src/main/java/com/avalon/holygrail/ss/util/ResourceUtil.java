package com.avalon.holygrail.ss.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;

/**
 * 资源文件读取工具
 */
public class ResourceUtil implements Serializable {

	/**  
	 * @Fields serialVersionUID : 序列化标识符
	 */ 
	private static final long serialVersionUID = -3889274686043327477L;
	
	/**系统语言环境-默认中文*/
	public static final String LANGUAGE = "zh";
	/**系统国家换将-默认中国*/
	public static final String COUNTRY = "CN";
	
	private static Locale getLocale() {
		return new Locale(LANGUAGE, COUNTRY);
	}
	
	/**
	 * 根据语言、国家、资源文件名、key名字获取资源文件值
	 * @param baseName 资源文件名
	 * @param section key名
	 */
	private static String getProperties(String baseName, String section) {
		String retValue = "";
		try {
			Locale locale = getLocale();
			ResourceBundle rb = ResourceBundle.getBundle(baseName, locale);
			retValue = (String) rb.getObject(section);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retValue;
	}
	
	/**
	 * 通过key从资源文件读取内容
	 * @param fileName 资源文件名
	 * @param key 索引
	 */
	public static String getValue(String fileName, String key) {
		return getProperties(fileName, key);
	}
	
	public static List<String> getKeyList(String baseName) {
		Locale locale = getLocale();
		ResourceBundle rb = ResourceBundle.getBundle(baseName, locale);
		
		List<String> list = new ArrayList<>();
		
		Set<String> keySet = rb.keySet();
		for(Iterator<String> it = keySet.iterator(); it.hasNext();) {
			list.add(it.next());
		}
		return list;
	}
	
	/**
	 * 通过key从资源文件读取内容并格式化
	 * @param fileName 资源文件名
	 * @param key 索引
	 * @param objs 格式化参数
	 */
	public static String getValue(String fileName, String key, Object[] objs) {
		String pattern = getValue(fileName, key);
		return MessageFormat.format(pattern, objs);
	}
	
}
