package com.avalon.holygrail.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 白超
 */
public class DateUtil {

    private DateUtil() {
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 获取YYYYMMDD 格式
     *
     * @return
     */
    public static String getTimeStringSimple() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static Long getTimeStamp() {
        return System.currentTimeMillis();
    }

}