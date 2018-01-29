package com.avalon.holygrail.ss.shiro.exception;

/**
 * Shiro异常
 * Created by 白超 on 2018/1/29.
 */
public class ShiroException extends Exception {

    /**
     * 真实异常
     */
    protected Exception realException = this;

    public ShiroException() {
    }

    public ShiroException(String message) {
        super(message);
    }

    public ShiroException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShiroException(Throwable cause) {
        super(cause);
    }

    public ShiroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ShiroException(Exception realException) {
        this.realException = realException;
    }

    public ShiroException(String message, Exception realException) {
        super(message);
        this.realException = realException;
    }

    public ShiroException(String message, Throwable cause, Exception realException) {
        super(message, cause);
        this.realException = realException;
    }

    public ShiroException(Throwable cause, Exception realException) {
        super(cause);
        this.realException = realException;
    }

    public ShiroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.realException = realException;
    }
}
