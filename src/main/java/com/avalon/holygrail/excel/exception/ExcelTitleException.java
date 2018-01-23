package com.avalon.holygrail.excel.exception;

import com.avalon.holygrail.excel.model.ExcelCellError;

/**
 * Created by 白超 on 2018/1/16.
 */
public class ExcelTitleException extends Exception {

    private ExcelCellError excelCellError;

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

    @Override
    public String toString() {
        return "row:" + excelCellError.getRow()
                + " col:" + excelCellError.getCol()
                + " 已被占用,title:"
                + excelCellError.getCellOption().getValue()
                + " rowSpan:" + excelCellError.getCellOption().getRowSpan()
                + " colSpan:" + excelCellError.getCellOption().getColSpan();
    }

}
