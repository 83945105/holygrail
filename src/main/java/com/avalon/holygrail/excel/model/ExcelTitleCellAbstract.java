package com.avalon.holygrail.excel.model;

import com.avalon.holygrail.excel.norm.CellOption;
import com.avalon.holygrail.excel.norm.CellStyle;

/**
 * Excel表头单元格属性
 * Created by 白超 on 2018/1/16.
 */
public abstract class ExcelTitleCellAbstract extends ExcelCellAbstract implements CellOption, CellStyle {
    /**
     * 列标题文本
     */
    protected String title = "";
    /**
     * 水平对齐方式-默认左对齐
     */
    protected String HAlign = "center";
    /**
     * 垂直对齐方式-默认居中对齐
     */
    protected String VAlign = "center";

    public ExcelTitleCellAbstract() {
    }

    public ExcelTitleCellAbstract(String field) {
        this.field = field;
    }

    public ExcelTitleCellAbstract(String title, String field) {
        this.title = title;
        this.field = field;
    }

    public ExcelTitleCellAbstract(String title, String field, Integer rowSpan, Integer colSpan) {
        this.title = title;
        this.field = field;
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
    }

    public ExcelTitleCellAbstract(String title, String field, Integer width, Integer rowSpan, Integer colSpan, String HAlign, String VAlign) {
        this.title = title;
        this.field = field;
        this.width = width;
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
        this.HAlign = HAlign.toLowerCase();
        this.VAlign = VAlign.toLowerCase();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Object getValue() {
        return getTitle();
    }

    @Override
    public void setValue(Object value) {
        setTitle(value == null ? "" : value.toString());
    }

    @Override
    public H_AlignType getHAlign() {
        return H_AlignType.getHAlignByName(HAlign);
    }

    @Override
    public void setHAlign(String HAlign) {
        this.HAlign = HAlign.toLowerCase();
    }

    @Override
    public V_AlignType getVAlign() {
        return V_AlignType.getVAlignByName(VAlign);
    }

    @Override
    public void setVAlign(String VAlign) {
        this.VAlign = VAlign.toLowerCase();
    }

}
