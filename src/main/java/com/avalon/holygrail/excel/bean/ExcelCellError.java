package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.norm.CellOption;

/**
 * Excel单元格错误信息
 * Created by 白超 on 2018/1/16.
 */
public class ExcelCellError {

    /**
     * 行号
     */
    private int row;
    /**
     * 列号
     */
    private int col;
    /**
     * 表头属性
     */
    private CellOption cellOption;

    public ExcelCellError() {
    }

    public ExcelCellError(int row, int col, CellOption cellOption) {
        this.row = row;
        this.col = col;
        this.cellOption = cellOption;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public CellOption getCellOption() {
        return cellOption;
    }

    public void setCellOption(CellOption cellOption) {
        this.cellOption = cellOption;
    }
}
