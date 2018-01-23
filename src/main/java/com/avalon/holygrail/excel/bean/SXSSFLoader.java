package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.norm.CellOption;
import com.avalon.holygrail.excel.norm.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * SXSSF装载器
 * Created by 白超 on 2018/1/18.
 */
public class SXSSFLoader implements CellOption, CellStyle {

    /**
     * SXSSF工作表
     */
    protected SXSSFSheet sheet;
    /**
     * SXSSF单元格
     */
    protected SXSSFCell cell;
    /**
     * SXSSF样式
     */
    protected XSSFCellStyle cellStyle;

    public SXSSFLoader(SXSSFSheet sheet, SXSSFCell cell, XSSFCellStyle cellStyle) {
        this.sheet = sheet;
        this.cell = cell;
        this.cellStyle = cellStyle;
    }

    @Override
    public CellType getType() {
        return null;
    }

    @Override
    public void setType(String type) {

    }

    @Override
    public String[] getOptions() {
        return new String[0];
    }

    @Override
    public void setOptions(String[] options) {

    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("");
            return;
        }
        if (value instanceof String) {
            cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
            cell.setCellValue((String) value);
            return;
        }
        if (value instanceof Integer) {
            cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue((int) value);
            return;
        }
        if (value instanceof Boolean) {
            cell.setCellType(SXSSFCell.CELL_TYPE_BOOLEAN);
            cell.setCellValue((boolean) value);
            return;
        }
    }

    @Override
    public String getField() {
        return null;
    }

    @Override
    public void setField(String field) {

    }

    @Override
    public Integer getWidth() {
        return null;
    }

    @Override
    public void setWidth(Integer width) {

    }

    @Override
    public Integer getRowSpan() {
        return null;
    }

    @Override
    public void setRowSpan(Integer rowSpan) {

    }

    @Override
    public Integer getColSpan() {
        return null;
    }

    @Override
    public void setColSpan(Integer colSpan) {

    }

    @Override
    public H_AlignType getHAlign() {
        return H_AlignType.getHAlignByValue(cellStyle.getAlignment());
    }

    @Override
    public void setHAlign(String HAlign) {
        cellStyle.setAlignment(H_AlignType.getHAlignByName(HAlign).value);
    }

    @Override
    public V_AlignType getVAlign() {
        return V_AlignType.getVAlignByValue(cellStyle.getVerticalAlignment());
    }

    @Override
    public void setVAlign(String VAlign) {
        cellStyle.setVerticalAlignment(V_AlignType.getVAlignByName(VAlign).value);
    }

    @Override
    public BorderStyle getBorderLeft() {
        return BorderStyle.getBorderStyleByValue(cellStyle.getBorderLeft());
    }

    @Override
    public void setBorderLeft(String borderLeft) {
        cellStyle.setBorderLeft(BorderStyle.getBorderStyleByName(borderLeft).value);
    }

    @Override
    public BorderStyle getBorderTop() {
        return BorderStyle.getBorderStyleByValue(cellStyle.getBorderTop());
    }

    @Override
    public void setBorderTop(String borderTop) {
        cellStyle.setBorderTop(BorderStyle.getBorderStyleByName(borderTop).value);
    }

    @Override
    public BorderStyle getBorderRight() {
        return BorderStyle.getBorderStyleByValue(cellStyle.getBorderRight());
    }

    @Override
    public void setBorderRight(String borderRight) {
        cellStyle.setBorderRight(BorderStyle.getBorderStyleByName(borderRight).value);
    }

    @Override
    public BorderStyle getBorderBottom() {
        return BorderStyle.getBorderStyleByValue(cellStyle.getBorderBottom());
    }

    @Override
    public void setBorderBottom(String borderBottom) {
        cellStyle.setBorderBottom(BorderStyle.getBorderStyleByName(borderBottom).value);
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

    public SXSSFSheet getSheet() {
        return sheet;
    }

    public void setSheet(SXSSFSheet sheet) {
        this.sheet = sheet;
    }

    public SXSSFCell getCell() {
        return cell;
    }

    public void setCell(SXSSFCell cell) {
        this.cell = cell;
    }

    public XSSFCellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(XSSFCellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }
}
