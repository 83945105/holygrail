package pub.avalon.holygrail.excel.exception;

/**
 * 导出异常
 *
 * @author 白超
 * @date 2018/1/23
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

}
