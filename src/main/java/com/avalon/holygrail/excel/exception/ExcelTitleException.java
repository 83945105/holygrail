package com.avalon.holygrail.excel.exception;

import com.avalon.holygrail.excel.model.ExcelCellError;

/**
 * Excel表头异常
 * Created by 白超 on 2018/1/16.
 */
public class ExcelTitleException extends ExcelException {

    protected ExcelCellError excelCellError;

    public ExcelTitleException() {
    }

    public ExcelTitleException(String message) {
        super(message);
    }

    public ExcelTitleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelTitleException(Throwable cause) {
        super(cause);
    }

    public ExcelTitleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExcelTitleException(Exception realException) {
        super(realException);
    }

    public ExcelTitleException(String message, Exception realException) {
        super(message, realException);
    }

    public ExcelTitleException(String message, Throwable cause, Exception realException) {
        super(message, cause, realException);
    }

    public ExcelTitleException(Throwable cause, Exception realException) {
        super(cause, realException);
    }

    public ExcelTitleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException) {
        super(message, cause, enableSuppression, writableStackTrace, realException);
    }

    public ExcelTitleException(ExcelCellError excelCellError) {
        this.excelCellError = excelCellError;
    }

    public ExcelTitleException(String message, ExcelCellError excelCellError) {
        super(message);
        this.excelCellError = excelCellError;
    }

    public ExcelTitleException(String message, Throwable cause, ExcelCellError excelCellError) {
        super(message, cause);
        this.excelCellError = excelCellError;
    }

    public ExcelTitleException(Throwable cause, ExcelCellError excelCellError) {
        super(cause);
        this.excelCellError = excelCellError;
    }

    public ExcelTitleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExcelCellError excelCellError) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.excelCellError = excelCellError;
    }

    public ExcelTitleException(Exception realException, ExcelCellError excelCellError) {
        super(realException);
        this.excelCellError = excelCellError;
    }

    public ExcelTitleException(String message, Exception realException, ExcelCellError excelCellError) {
        super(message, realException);
        this.excelCellError = excelCellError;
    }

    public ExcelTitleException(String message, Throwable cause, Exception realException, ExcelCellError excelCellError) {
        super(message, cause, realException);
        this.excelCellError = excelCellError;
    }

    public ExcelTitleException(Throwable cause, Exception realException, ExcelCellError excelCellError) {
        super(cause, realException);
        this.excelCellError = excelCellError;
    }

    public ExcelTitleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception realException, ExcelCellError excelCellError) {
        super(message, cause, enableSuppression, writableStackTrace, realException);
        this.excelCellError = excelCellError;
    }

    @Override
    public String toString() {
        try {
            return "row:" + excelCellError.getRow()
                    + " col:" + excelCellError.getCol()
                    + " 已被占用,title:"
                    + excelCellError.getCellOption().getValue()
                    + " rowSpan:" + excelCellError.getCellOption().getRowSpan()
                    + " colSpan:" + excelCellError.getCellOption().getColSpan();
        } catch (ExcelException e) {
            e.printStackTrace();
        }
        return super.toString();
    }

}
