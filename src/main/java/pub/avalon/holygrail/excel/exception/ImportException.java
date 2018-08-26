package pub.avalon.holygrail.excel.exception;

/**
 * 导入异常
 *
 * @author 白超
 * @date 2018/1/24
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

}
