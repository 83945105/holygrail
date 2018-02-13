package com.avalon.holygrail.promise.exception;

/**
 * 承诺模式异常
 * Created by 白超 on 2018-2-13.
 */
public class PromiseException extends Exception {

    /**
     * 错误对象
     */
    protected Object error;

    public PromiseException() {
    }

    public PromiseException(String message) {
        super(message);
    }

    public PromiseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PromiseException(Throwable cause) {
        super(cause);
    }

    public PromiseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PromiseException(Object error) {
        this.error = error;
    }

    public PromiseException(String message, Object error) {
        super(message);
        this.error = error;
    }

    public PromiseException(String message, Throwable cause, Object error) {
        super(message, cause);
        this.error = error;
    }

    public PromiseException(Throwable cause, Object error) {
        super(cause);
        this.error = error;
    }

    public PromiseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object error) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.error = error;
    }
}
