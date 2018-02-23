package com.avalon.holygrail.promise.exception;

/**
 * 被拒绝异常
 * 用于记录用户使用reject.apply()设置的异常
 * Created by 白超 on 2018-2-23.
 */
public class RejectedException extends PromiseException {

    public RejectedException() {
    }

    public RejectedException(String message) {
        super(message);
    }

    public RejectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RejectedException(Throwable cause) {
        super(cause);
    }

    public RejectedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RejectedException(Object error) {
        super(error);
    }

    public RejectedException(String message, Object error) {
        super(message, error);
    }

    public RejectedException(String message, Throwable cause, Object error) {
        super(message, cause, error);
    }

    public RejectedException(Throwable cause, Object error) {
        super(cause, error);
    }

    public RejectedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object error) {
        super(message, cause, enableSuppression, writableStackTrace, error);
    }
}
