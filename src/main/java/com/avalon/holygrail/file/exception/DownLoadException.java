package com.avalon.holygrail.file.exception;

import com.avalon.holygrail.file.bean.DownloadRecord;

/**
 * 下载异常
 * Created by 白超 on 2018/1/22.
 */
public class DownLoadException extends Exception {

    private DownloadRecord downloadRecord;

    public DownLoadException(DownloadRecord downloadRecord) {
        this.downloadRecord = downloadRecord;
    }

    public DownLoadException(String message, DownloadRecord downloadRecord) {
        super(message);
        this.downloadRecord = downloadRecord;
    }

    public DownLoadException(String message, Throwable cause, DownloadRecord downloadRecord) {
        super(message, cause);
        this.downloadRecord = downloadRecord;
    }

    public DownLoadException(Throwable cause, DownloadRecord downloadRecord) {
        super(cause);
        this.downloadRecord = downloadRecord;
    }

    public DownLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, DownloadRecord downloadRecord) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.downloadRecord = downloadRecord;
    }
}
