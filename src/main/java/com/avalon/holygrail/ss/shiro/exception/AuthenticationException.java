package com.avalon.holygrail.ss.shiro.exception;

/**
 * 没有权限异常
 * Created by 白超 on 2018/1/29.
 */
public class AuthenticationException extends ShiroException {

    public AuthenticationException() {
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthenticationException(Exception realException) {
        super(realException);
    }

    public AuthenticationException(String message, Exception realException) {
        super(message, realException);
    }

    public AuthenticationException(String message, Throwable cause, Exception realException) {
        super(message, cause, realException);
    }

    public AuthenticationException(Throwable cause, Exception realException) {
        super(cause, realException);
    }

    public AuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace, realException);
    }
}
