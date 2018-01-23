package com.avalon.holygrail.excel.model;

import com.avalon.holygrail.excel.norm.CellOption;
import com.avalon.holygrail.excel.norm.CellStyle;

/**
 * Excel表头
 * Created by 白超 on 2018/1/16.
 */
public abstract class ExcelTitleAbstract extends ExcelCellAbstract implements CellOption, CellStyle {
    /**
     * 单元格类型
     */
    protected String type = "text";
    /**
     * 列标题文本
     */
    protected String title = "";
    /**
     * 列字段名称
     */
    protected String field;
    /**
     * 列宽-默认10
     */
    protected Integer width = 10;
    /**
     * 占用多少行单元格(合并行)-默认1
     */
    protected Integer rowSpan = 1;
    /**
     * 占用多少列单元格(合并列)-默认1
     */
    protected Integer colSpan = 1;
    /**
     * 水平对齐方式-默认左对齐
     */
    protected String HAlign = "center";
    /**
     * 垂直对齐方式-默认顶部对齐
     */
    protected String VAlign = "center";
    /**
     * 左边框
     */
    protected String borderLeft = "none";
    /**
     * 上边框
     */
    protected String borderTop = "none";
    /**
     * 右边框
     */
    protected String borderRight = "none";
    /**
     * 下边框
     */
    protected String borderBottom = "none";

    public ExcelTitleAbstract() {
    }

    public ExcelTitleAbstract(String field) {
        this.field = field;
    }

    public ExcelTitleAbstract(String title, String field) {
        this.title = title;
        this.field = field;
    }

    public ExcelTitleAbstract(String title, String field, Integer rowSpan, Integer colSpan) {
        this.title = title;
        this.field = field;
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
    }

    public ExcelTitleAbstract(String title, String field, Integer width, Integer rowSpan, Integer colSpan, String HAlign, String VAlign) {
        this.title = title;
        this.field = field;
        this.width = width;
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
        this.HAlign = HAlign.toLowerCase();
        this.VAlign = VAlign.toLowerCase();
    }

    @Override
    public CellType getType() {
        return CellType.getCellTypeByName(type);
    }

    @Override
    public void setType(String type) {
        this.type = type.toLowerCase();
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
    public String getField() {
        return field;
    }

    @Override
    public void setField(String field) {
        this.field = field;
    }

    @Override
    public Integer getWidth() {
        return width;
    }

    @Override
    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public Integer getRowSpan() {
        return rowSpan;
    }

    @Override
    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    @Override
    public Integer getColSpan() {
        return colSpan;
    }

    @Override
    public void setColSpan(Integer colSpan) {
        this.colSpan = colSpan;
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

    @Override
    public BorderStyle getBorderLeft() {
        return BorderStyle.getBorderStyleByName(borderLeft);
    }

    @Override
    public void setBorderLeft(String borderLeft) {
        this.borderLeft = borderLeft.toLowerCase();
    }

    @Override
    public BorderStyle getBorderTop() {
        return BorderStyle.getBorderStyleByName(borderTop);
    }

    @Override
    public void setBorderTop(String borderTop) {
        this.borderTop = borderTop.toLowerCase();
    }

    @Override
    public BorderStyle getBorderRight() {
        return BorderStyle.getBorderStyleByName(borderRight);
    }

    @Override
    public void setBorderRight(String borderRight) {
        this.borderRight = borderRight.toLowerCase();
    }

    @Override
    public BorderStyle getBorderBottom() {
        return BorderStyle.getBorderStyleByName(borderBottom);
    }

    @Override
    public void setBorderBottom(String borderBottom) {
        this.borderBottom = borderBottom.toLowerCase();
    }

    @Override
    public BorderStyle[] getBorder() {
        return new BorderStyle[]{
                this.getBorderLeft(),
                this.getBorderTop(),
                this.getBorderRight(),
                this.getBorderBottom()
        };
    }

    @Override
    public void setBorder(String border) {
        String[] borders = border.split(",");
        this.setBorderLeft(borders[0]);
        this.setBorderTop(borders[1]);
        this.setBorderRight(borders[2]);
        this.setBorderBottom(borders[3]);
    }
}
