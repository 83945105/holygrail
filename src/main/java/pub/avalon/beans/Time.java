package pub.avalon.beans;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间
 *
 * @author 白超
 * @date 2018/8/24
 */
public interface Time {

    String DATE_TIME_SIMPLE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    static String localDateNow() {
        return LocalDate.now().toString();
    }

    /**
     * 获取当前时间
     *
     * @return HH:mm:ss.SSSZ
     */
    static String localTimeNow() {
        return LocalTime.now().toString();
    }

    /**
     * 获取当前时间
     *
     * @return yyyy-MM-ddTHH:mm:ss.SSSZ
     */
    static String localDateTimeNow() {
        return LocalDateTime.now().toString();
    }

    /**
     * 获取时间戳
     *
     * @return 毫秒级时间戳
     */
    static long timeStamp() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 获取当前简化字符串时间
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    static String localDateTimeNowSimple() {
        return Time.localDateNow() + " " + Time.localTimeNow().substring(0, 8);
    }

    /**
     * 时间戳转为简化版字符串日期时间
     * yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    static String timeStampToSimpleDateTimeString(long timestamp) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_SIMPLE_FORMATTER);
        return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
    }

    /**
     * 简化字符串日期时间转时间戳
     * yyyy-MM-dd HH:mm:ss
     *
     * @param timeStr
     * @return
     */
    static long simpleDateTimeStringToTimeStamp(String timeStr) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(DATE_TIME_SIMPLE_FORMATTER);
        LocalDateTime parse = LocalDateTime.parse(timeStr, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 时间戳转为字符串时间
     * yyyy-MM-ddTHH:mm:ss.SSS
     *
     * @param timestamp
     * @return
     */
    static String timeStampToString(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).toString();
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     */
    static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     */
    static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 获取指定日期之前N天的日期
     *
     * @param date
     * @param day
     * @return
     */
    static Date dateTimeBeforeDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
        return calendar.getTime();
    }

    /**
     * 计算2个日期之间相差的年月日
     * 例: 2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    static int[] simpleDateDisparityYearMonthDay(Date fromDate, Date toDate) {
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear - fromYear;
        int month = toMonth - fromMonth;
        int day = toDay - fromDay;
        return new int[]{year, month, day};
    }

    /**
     * 计算2个日期之间相差的 总计 年月日
     * 例: 2011-02-02 到  2017-03-02
     * 以年为单位相差为：6年
     * 以月为单位相差为：73个月
     * 以日为单位相差为：2220天
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    static int[] simpleDateDisparityTotalYearMonthDay(Date fromDate, Date toDate) {
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);
        //只要年月
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);

        int year = toYear - fromYear;
        int month = toYear * 12 + toMonth - (fromYear * 12 + fromMonth);
        int day = (int) ((to.getTimeInMillis() - from.getTimeInMillis()) / (24 * 3600 * 1000));
        return new int[]{year, month, day};
    }

    /**
     * 判断日期是否是该月第一天
     *
     * @param date
     * @return
     */
    static boolean simpleDateIsFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }

}
