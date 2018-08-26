package pub.avalon.holygrail.excel.exception;

import pub.avalon.holygrail.excel.bean.ExcelTitleCellError;

/**
 * Excel表头异常
 *
 * @author 白超
 * @date 2018/1/16
 */
public class ExcelTitleException extends ExcelException {

    protected ExcelTitleCellError excelTitleCellError;

    public ExcelTitleException(ExcelTitleCellError excelTitleCellError) {
        this.excelTitleCellError = excelTitleCellError;
    }

    public ExcelTitleException(String message, ExcelTitleCellError excelTitleCellError) {
        super(message);
        this.excelTitleCellError = excelTitleCellError;
    }

    public ExcelTitleException(String message, Throwable cause, ExcelTitleCellError excelTitleCellError) {
        super(message, cause);
        this.excelTitleCellError = excelTitleCellError;
    }

    public ExcelTitleException(Throwable cause, ExcelTitleCellError excelTitleCellError) {
        super(cause);
        this.excelTitleCellError = excelTitleCellError;
    }

    public ExcelTitleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExcelTitleCellError excelTitleCellError) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.excelTitleCellError = excelTitleCellError;
    }

    @Override
    public String toString() {
        try {
            return "row:" + excelTitleCellError.getRow()
                    + " col:" + excelTitleCellError.getCol()
                    + " 已被占用,title:"
                    + excelTitleCellError.getExcelTitleCellHandler().getTitle()
                    + " rowSpan:" + excelTitleCellError.getExcelTitleCellHandler().getRowSpan()
                    + " colSpan:" + excelTitleCellError.getExcelTitleCellHandler().getColSpan();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.toString();
    }

}
