package com.avalon.holygrail.excel.model;

import com.avalon.holygrail.excel.norm.CellStyle;
import com.avalon.holygrail.excel.norm.ExcelCellHandler;
import com.avalon.holygrail.excel.norm.ExcelWorkBookExport;
import com.avalon.holygrail.excel.norm.Font;

/**
 * Excel cell 基础类
 *
 * @author 白超
 * @date 2018/4/10
 */
public abstract class BaseExcelCell extends BaseCell implements ExcelCellHandler {

    private ExcelWorkBookExport excelWorkBookExport;

    /**
     * 单元格样式
     */
    protected CellStyle cellStyle;

    public BaseExcelCell(ExcelWorkBookExport excelWorkBookExport, CellStyle cellStyle, int startRowNum, int startColNum) {
        super(startRowNum, startColNum);
        this.excelWorkBookExport = excelWorkBookExport;
        this.cellStyle = cellStyle;
    }

    public BaseExcelCell(ExcelWorkBookExport excelWorkBookExport, CellStyle cellStyle, int startRowNum, int startColNum, int rowSpan, int colSpan) {
        super(startRowNum, startColNum, rowSpan, colSpan);
        this.excelWorkBookExport = excelWorkBookExport;
        this.cellStyle = cellStyle;
    }

    /**
     * 单元格文本
     */
    protected Object value;

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getCellValue() {
        return this.getValue();
    }

    @Override
    public CellStyle findCellStyle(int index) {
        return this.excelWorkBookExport.findCellStyle(index);
    }

    @Override
    public Font findFont(int index) {
        return this.excelWorkBookExport.findFont(index);
    }

    @Override
    public CellStyle getCellStyle() {
        return this.cellStyle;
    }

    @Override
    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

}
