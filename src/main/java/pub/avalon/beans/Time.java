package pub.avalon.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 时间
 *
 * @author 白超
 * @date 2018/8/24
 */
public interface Time {

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
        return System.currentTimeMillis();
    }

    static String localDateTimeNowSimple() {
        return Time.localDateNow() + " " + Time.localTimeNow().substring(0, 8);
    }

}
