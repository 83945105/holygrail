package com.avalon.holygrail.excel.exception;

/**
 * 导出异常
 * Created by 白超 on 2018/1/23.
 */
public class ExportException extends ExcelException {

    public ExportException() {
    }

    public ExportException(String message) {
        super(message);
    }

    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExportException(Throwable cause) {
        super(cause);
    }

    public ExportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExportException(Exception realException) {
        super(realException);
    }

    public ExportException(String message, Exception realException) {
        super(message, realException);
    }

    public ExportException(String message, Throwable cause, Exception realException) {
        super(message, cause, realException);
    }

    public ExportException(Throwable cause, Exception realException) {
        super(cause, realException);
    }

    public ExportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace, realException);
    }
}
