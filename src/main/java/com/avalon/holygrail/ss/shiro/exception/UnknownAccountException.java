package com.avalon.holygrail.ss.shiro.exception;

/**
 * 未知账户异常
 * Created by 白超 on 2018/1/29.
 */
public class UnknownAccountException extends ShiroException {

    public UnknownAccountException() {
    }

    public UnknownAccountException(String message) {
        super(message);
    }

    public UnknownAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownAccountException(Throwable cause) {
        super(cause);
    }

    public UnknownAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnknownAccountException(Exception realException) {
        super(realException);
    }

    public UnknownAccountException(String message, Exception realException) {
        super(message, realException);
    }

    public UnknownAccountException(String message, Throwable cause, Exception realException) {
        super(message, cause, realException);
    }

    public UnknownAccountException(Throwable cause, Exception realException) {
        super(cause, realException);
    }

    public UnknownAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace, realException);
    }
}
