package com.avalon.holygrail.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat YYYY_MM_DD__HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTimeString() {
        return YYYY_MM_DD__HH_MM_SS.format(new Date());
    }

    /**
     * 获取YYYYMMDD 格式
     *
     * @return
     */
    public static String getTimeStringSimple() {
        return YYYYMMDD.format(new Date());
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static Long getTimeStamp() {
        return new Date().getTime();
    }

}
