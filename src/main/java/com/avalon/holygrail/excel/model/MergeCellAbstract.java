package com.avalon.holygrail.excel.model;

import com.avalon.holygrail.excel.norm.CellHandler;
import com.avalon.holygrail.excel.norm.CellOption;
import com.avalon.holygrail.excel.norm.CellStyle;
import com.avalon.holygrail.excel.norm.MergeCell;

/**
 * 合并单元格属性
 * Created by 白超 on 2018/1/17.
 */
public abstract class MergeCellAbstract extends ExcelCellAbstract implements MergeCell, CellOption, CellStyle, CellHandler {

    /**
     * 垂直对齐方式-默认顶部对齐
     */
    protected String VAlign = "center";

    /**
     * 开始行
     */
    protected Integer startRow;

    /**
     * 开始列
     */
    protected Integer startCol;

    public MergeCellAbstract(Integer startRow, Integer startCol) {
        this.startRow = startRow;
        this.startCol = startCol;
    }

    public MergeCellAbstract(Integer startRow, Integer startCol, Integer rowSpan, Integer colSpan) {
        super(rowSpan, colSpan);
        this.startRow = startRow;
        this.startCol = startCol;
    }

    @Override
    public void setHAlign(H_AlignType hAlignType) {
        this.setHAlign(hAlignType.name());
    }

    @Override
    public void setHAlign(short value) {
        this.setHAlign(H_AlignType.getHAlignByValue(value));
    }

    @Override
    public V_AlignType getVAlign() {
        return V_AlignType.getVAlignByName(VAlign);
    }

    @Override
    public void setVAlign(String VAlign) {
        this.VAlign = VAlign.toLowerCase();
    }

    @Override
    public void setVAlign(V_AlignType vAlignType) {
        this.setVAlign(vAlignType.name());
    }

    @Override
    public void setVAlign(short value) {
        this.setVAlign(V_AlignType.getVAlignByValue(value));
    }

    @Override
    public void setBorderLeft(BorderStyle borderStyle) {
        this.setBorderLeft(borderStyle.name());
    }

    @Override
    public void setBorderLeft(short value) {
        this.setBorderLeft(BorderStyle.getBorderStyleByValue(value));
    }

    @Override
    public void setBorderTop(BorderStyle borderStyle) {
        this.setBorderTop(borderStyle.name());
    }

    @Override
    public void setBorderTop(short value) {
        this.setBorderTop(BorderStyle.getBorderStyleByValue(value));
    }

    @Override
    public void setBorderRight(BorderStyle borderStyle) {
        this.setBorderRight(borderStyle.name());
    }

    @Override
    public void setBorderRight(short value) {
        this.setBorderRight(BorderStyle.getBorderStyleByValue(value));
    }

    @Override
    public void setBorderBottom(BorderStyle borderStyle) {
        this.setBorderBottom(borderStyle.name());
    }

    @Override
    public void setBorderBottom(short value) {
        this.setBorderBottom(BorderStyle.getBorderStyleByValue(value));
    }

    @Override
    public void setBorder(String border) {
        String[] borders = border.split(",");
        this.setBorderLeft(borders[0]);
        this.setBorderTop(borders[1]);
        this.setBorderRight(borders[2]);
        this.setBorderBottom(borders[3]);
    }

    @Override
    public void setBorder(BorderStyle[] borderStyles) {
        StringBuilder sb = new StringBuilder();
        for (BorderStyle borderStyle : borderStyles) {
            sb.append(",").append(borderStyle.name());
        }
        this.setBorder(sb.substring(1));
    }

    @Override
    public void setBorder(short[] values) {
        this.setBorderLeft(values[0]);
        this.setBorderTop(values[1]);
        this.setBorderRight(values[2]);
        this.setBorderBottom(values[3]);
    }

    @Override
    public Integer getStartRow() {
        return this.startRow;
    }

    @Override
    public Integer getEndRow() {
        return this.startRow + this.rowSpan - 1;
    }

    @Override
    public Integer getStartCol() {
        return startCol;
    }

    @Override
    public Integer getEndCol() {
        return this.startCol + this.colSpan - 1;
    }

    @Override
    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    @Override
    public void setStartCol(Integer startCol) {
        this.startCol = startCol;
    }

    @Override
    public void setType(CellType type) {
        this.setType(type.name());
    }

    @Override
    public void setType(short value) {
        this.setType(CellType.getCellTypeByValue(value));
    }

}
