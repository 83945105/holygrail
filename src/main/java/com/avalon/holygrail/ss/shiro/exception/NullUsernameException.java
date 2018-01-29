package com.avalon.holygrail.ss.shiro.exception;

/**
 * 空用户名异常
 * Created by 白超 on 2018/1/29.
 */
public class NullUsernameException extends IncorrectCredentialsException {

    public NullUsernameException() {
    }

    public NullUsernameException(String message) {
        super(message);
    }

    public NullUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullUsernameException(Throwable cause) {
        super(cause);
    }

    public NullUsernameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NullUsernameException(Exception realException) {
        super(realException);
    }

    public NullUsernameException(String message, Exception realException) {
        super(message, realException);
    }

    public NullUsernameException(String message, Throwable cause, Exception realException) {
        super(message, cause, realException);
    }

    public NullUsernameException(Throwable cause, Exception realException) {
        super(cause, realException);
    }

    public NullUsernameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace, realException);
    }
}
