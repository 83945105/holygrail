package com.avalon.holygrail.ss.shiro.exception;

/**
 * 空密码异常
 * Created by 白超 on 2018/1/29.
 */
public class NullPasswordException extends IncorrectCredentialsException {


    public NullPasswordException() {
    }

    public NullPasswordException(String message) {
        super(message);
    }

    public NullPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullPasswordException(Throwable cause) {
        super(cause);
    }

    public NullPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NullPasswordException(Exception realException) {
        super(realException);
    }

    public NullPasswordException(String message, Exception realException) {
        super(message, realException);
    }

    public NullPasswordException(String message, Throwable cause, Exception realException) {
        super(message, cause, realException);
    }

    public NullPasswordException(Throwable cause, Exception realException) {
        super(cause, realException);
    }

    public NullPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace, realException);
    }
}
