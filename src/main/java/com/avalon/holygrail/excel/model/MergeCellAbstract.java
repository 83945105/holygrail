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
     * 开始行号
     */
    protected Integer startRowNum;

    /**
     * 开始列号
     */
    protected Integer startColNum;

    public MergeCellAbstract(Integer startRowNum, Integer startColNum) {
        this.startRowNum = startRowNum;
        this.startColNum = startColNum;
    }

    public MergeCellAbstract(Integer startRowNum, Integer startColNum, Integer rowSpan, Integer colSpan) {
        super(rowSpan, colSpan);
        this.startRowNum = startRowNum;
        this.startColNum = startColNum;
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
    public Integer getStartRowNum() {
        return this.startRowNum;
    }

    @Override
    public Integer getEndRowNum() {
        return this.startRowNum + this.rowSpan - 1;
    }

    @Override
    public Integer getStartColNum() {
        return startColNum;
    }

    @Override
    public Integer getEndColNum() {
        return this.startColNum + this.colSpan - 1;
    }

    @Override
    public void setStartRowNum(Integer startRowNum) {
        this.startRowNum = startRowNum;
    }

    @Override
    public void setStartColNum(Integer startColNum) {
        this.startColNum = startColNum;
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
