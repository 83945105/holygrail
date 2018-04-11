package com.avalon.holygrail.excel.model;

import com.avalon.holygrail.excel.norm.CellHandler;
import com.avalon.holygrail.excel.norm.CellOption;

/**
 * Cell基础类
 * Created by 白超 on 2018/1/17.
 */
public abstract class BaseCell implements CellHandler {

    public BaseCell() {
    }

    public BaseCell(int startRowNum, int startColNum) {
        this.startRowNum = startRowNum;
        this.startColNum = startColNum;
    }

    public BaseCell(int startRowNum, int startColNum, int rowSpan, int colSpan) {
        this.startRowNum = startRowNum;
        this.startColNum = startColNum;
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
    }

    /**
     * 获取开始行号
     */
    protected int startRowNum;
    /**
     * 获取开始列号
     */
    protected int startColNum;

    /**
     * 单元格类型
     */
    protected CellOption.CellType cellType = CellOption.CellType.TEXT;
    /**
     * 下拉框值
     */
    protected String[] cellOptions = {};
    /**
     * 单元格字段名称
     */
    protected String field;
    /**
     * 占用多少行单元格(合并行)-默认1
     */
    protected int rowSpan = 1;
    /**
     * 占用多少列单元格(合并列)-默认1
     */
    protected int colSpan = 1;

    /**
     * 当该列值为空时,是否写入数据
     */
    protected boolean writeEmpty = true;

    @Override
    public int getStartRowNum() {
        return this.startRowNum;
    }

    @Override
    public int getEndRowNum() {
        return this.startRowNum + this.rowSpan - 1;
    }

    @Override
    public int getStartColNum() {
        return this.startColNum;
    }

    @Override
    public int getEndColNum() {
        return this.startColNum + this.colSpan - 1;
    }

    @Override
    public String getField() {
        return this.field;
    }

    @Override
    public CellOption.CellType getCellType() {
        return this.cellType;
    }

    @Override
    public String[] getCellOptions() {
        return this.cellOptions;
    }

    @Override
    public int getRowSpan() {
        return this.rowSpan;
    }

    @Override
    public int getColSpan() {
        return this.colSpan;
    }

    @Override
    public boolean isWriteEmpty() {
        return this.writeEmpty;
    }

    @Override
    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    @Override
    public void setStartColNum(int startColNum) {
        this.startColNum = startColNum;
    }

    @Override
    public void setField(String field) {
        this.field = field;
    }

    @Override
    public void setCellType(String cellTypeString) {
        this.cellType = CellOption.CellType.getCellTypeByName(cellTypeString);
    }

    @Override
    public void setCellType(CellOption.CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    public void setCellType(short cellTypeShort) {
        this.cellType = CellOption.CellType.getCellTypeByValue(cellTypeShort);
    }

    @Override
    public void setCellOptions(String[] cellOptions) {
        this.cellOptions = cellOptions;
    }

    @Override
    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    @Override
    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    @Override
    public void setWriteEmpty(boolean writeEmpty) {
        this.writeEmpty = writeEmpty;
    }

    /**
     * 获取单元格内容
     *
     * @return
     */
    public abstract Object getCellValue();

    @Override
    public boolean isMergeCell() {
        return this.getStartRowNum() != this.getEndRowNum() || this.getStartColNum() != this.getEndColNum();
    }

}
