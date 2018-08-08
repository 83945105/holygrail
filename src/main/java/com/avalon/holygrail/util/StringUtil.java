package com.avalon.holygrail.util;

import java.util.Collection;
import java.util.Map;

/**
 * 字符串工具
 * Created by 白超 on 2018/1/22.
 */
public class StringUtil {

    /**
     * 验证对象是否为空
     * null => true
     * Collection size == 0 => true
     * Map size == 0 => true
     * Array length == 0 => true
     *
     * @param tar 对象
     * @return 空 => true | 非空 => false
     */
    public static boolean isEmpty(Object tar) {
        if (tar == null) {
            return true;
        }
        if (tar instanceof String) {
            return tar.toString().length() == 0;
        }
        if (tar instanceof Collection) {
            return ((Collection) tar).size() == 0;
        }
        if (tar instanceof Map) {
            return ((Map) tar).size() == 0;
        }
        if (tar.getClass().isArray()) {
            return ((Object[]) tar).length == 0;
        }
        return false;
    }

    /**
     * 字符串转Unicode编码
     *
     * @param str 字符串
     * @return Unicode编码
     */
    public static String stringToUnicode(String str) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            unicode.append("\\u").append(Integer.toHexString(str.charAt(i)));
        }
        return unicode.toString();
    }

    /**
     * Unicode编码转字符串
     *
     * @param unicode Unicode编码
     * @return 字符串
     */
    public static String unicodeToString(String unicode) {
        StringBuilder str = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            str.append((char) data);
        }
        return str.toString();
    }

    /**
     * 字符串转换为16进制字符串
     *
     * @param str 字符串
     * @return 16进制字符串
     */
    public static String stringToHexString(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int ch = (int) str.charAt(i);
            String s4 = Integer.toHexString(ch);
            sb.append(s4);
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转为字符串
     *
     * @param hexStr 16进制
     * @return 字符串
     */
    public static String hexStringToString(String hexStr) {
        if (hexStr == null || "".equals(hexStr)) {
            return null;
        }
        hexStr = hexStr.replace(" ", "");
        byte[] baKeyword = new byte[hexStr.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        hexStr.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            hexStr = new String(baKeyword, "gbk");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hexStr;
    }

    /**
     * 字符串转16进制字符串
     *
     * @param str 字符串
     * @return 16进制字符串
     */
    public static String stringDirectToHexString(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }

    /**
     * 16进制字符串直接转字符串(无需转Unicode)
     *
     * @param hexStr 16进制字符串
     * @return 字符串
     */
    public static String hexStringDirectToString(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

}
