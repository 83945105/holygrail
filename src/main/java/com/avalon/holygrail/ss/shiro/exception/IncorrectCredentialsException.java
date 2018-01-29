package com.avalon.holygrail.ss.shiro.exception;

/**
 * 未知凭证异常
 * Created by 白超 on 2018/1/29.
 */
public class IncorrectCredentialsException extends ShiroException {

    public IncorrectCredentialsException() {
    }

    public IncorrectCredentialsException(String message) {
        super(message);
    }

    public IncorrectCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCredentialsException(Throwable cause) {
        super(cause);
    }

    public IncorrectCredentialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IncorrectCredentialsException(Exception realException) {
        super(realException);
    }

    public IncorrectCredentialsException(String message, Exception realException) {
        super(message, realException);
    }

    public IncorrectCredentialsException(String message, Throwable cause, Exception realException) {
        super(message, cause, realException);
    }

    public IncorrectCredentialsException(Throwable cause, Exception realException) {
        super(cause, realException);
    }

    public IncorrectCredentialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace, realException);
    }
}
