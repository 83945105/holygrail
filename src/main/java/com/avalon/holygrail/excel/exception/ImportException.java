package com.avalon.holygrail.excel.exception;

/**
 * 导入异常
 * Created by 白超 on 2018/1/24.
 */
public class ImportException extends ExcelException {

    public ImportException() {
    }

    public ImportException(String message) {
        super(message);
    }

    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportException(Throwable cause) {
        super(cause);
    }

    public ImportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ImportException(Exception realException) {
        super(realException);
    }

    public ImportException(String message, Exception realException) {
        super(message, realException);
    }

    public ImportException(String message, Throwable cause, Exception realException) {
        super(message, cause, realException);
    }

    public ImportException(Throwable cause, Exception realException) {
        super(cause, realException);
    }

    public ImportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace, realException);
    }
}
