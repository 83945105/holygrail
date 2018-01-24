package com.avalon.holygrail.excel.exception;

/**
 * Excel异常
 * Created by 白超 on 2018/1/24.
 */
public class ExcelException extends Exception {

    /**
     * 真实异常
     */
    protected Exception realException = this;

    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }

    public ExcelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExcelException(Exception realException) {
        this.realException = realException;
    }

    public ExcelException(String message, Exception realException) {
        super(message);
        this.realException = realException;
    }

    public ExcelException(String message, Throwable cause, Exception realException) {
        super(message, cause);
        this.realException = realException;
    }

    public ExcelException(Throwable cause, Exception realException) {
        super(cause);
        this.realException = realException;
    }

    public ExcelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.realException = realException;
    }
}
