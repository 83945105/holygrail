package com.avalon.holygrail.ss.shiro.exception;

/**
 * 账户冻结异常
 * Created by 白超 on 2018/1/29.
 */
public class LockedAccountException extends ShiroException {

    public LockedAccountException() {
    }

    public LockedAccountException(String message) {
        super(message);
    }

    public LockedAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockedAccountException(Throwable cause) {
        super(cause);
    }

    public LockedAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LockedAccountException(Exception realException) {
        super(realException);
    }

    public LockedAccountException(String message, Exception realException) {
        super(message, realException);
    }

    public LockedAccountException(String message, Throwable cause, Exception realException) {
        super(message, cause, realException);
    }

    public LockedAccountException(Throwable cause, Exception realException) {
        super(cause, realException);
    }

    public LockedAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace, realException);
    }
}
