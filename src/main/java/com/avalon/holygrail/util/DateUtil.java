package com.avalon.holygrail.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTimeString() {
        return sdfTime.format(new Date());
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
