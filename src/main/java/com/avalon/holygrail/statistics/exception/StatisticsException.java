package com.avalon.holygrail.statistics.exception;

/**
 * 统计异常
 * Created by 白超 on 2018/3/12.
 */
public class StatisticsException extends Exception {

    public StatisticsException() {
    }

    public StatisticsException(String message) {
        super(message);
    }

    public StatisticsException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatisticsException(Throwable cause) {
        super(cause);
    }

    public StatisticsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
