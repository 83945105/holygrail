package com.avalon.holygrail.excel.bean;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.norm.CellOption;
import com.avalon.holygrail.excel.norm.CellStyle;
import org.apache.poi.ss.usermodel.Cell;
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

    public SXSSFLoader(SXSSFSheet sheet, SXSSFCell cell, XSSFCellStyle cellStyle) {
        this.sheet = sheet;
        this.cell = cell;
        this.setCellStyle(cellStyle);
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
    public Object getValue() throws ExcelException {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue();
            default:
                throw new ExcelException("暂不支持获取该单元格类型值");
        }
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            cell.setCellType(SXSSFCell.CELL_TYPE_BLANK);
            return;
        }
        if (value instanceof String) {
            cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
            cell.setCellValue((String) value);
            return;
        }
        if (value instanceof Boolean) {
            cell.setCellType(SXSSFCell.CELL_TYPE_BOOLEAN);
            cell.setCellValue((boolean) value);
            return;
        }
        if (value instanceof Integer) {
            cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue((int) value);
            return;
        }
        if (value instanceof Long) {
            cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue((long) value);
            return;
        }

        if (value instanceof Double) {
            cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue((double) value);
            return;
        }
        if (value instanceof Float) {
            cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue((float) value);
            return;
        }
        if (value instanceof Short) {
            cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue((short) value);
            return;
        }
        if (value instanceof Byte) {
            cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue((byte) value);
            return;
        }
        if (value instanceof Char) {
            cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue((char) value);
            return;
        }
        cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
        cell.setCellValue((String) value);
        return;
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
    public boolean isWriteEmpty() {
        return false;
    }

    @Override
    public void setWriteEmpty(boolean writeEmpty) {

    }

    @Override
    public H_AlignType getHAlign() {
        return H_AlignType.getHAlignByValue(this.getCellStyle().getAlignment());
    }

    @Override
    public void setHAlign(String HAlign) {
        this.getCellStyle().setAlignment(H_AlignType.getHAlignByName(HAlign).value);
    }

    @Override
    public V_AlignType getVAlign() {
        return V_AlignType.getVAlignByValue(this.getCellStyle().getVerticalAlignment());
    }

    @Override
    public void setVAlign(String VAlign) {
        this.getCellStyle().setVerticalAlignment(V_AlignType.getVAlignByName(VAlign).value);
    }

    @Override
    public BorderStyle getBorderLeft() {
        return BorderStyle.getBorderStyleByValue(this.getCellStyle().getBorderLeft());
    }

    @Override
    public void setBorderLeft(String borderLeft) {
        this.getCellStyle().setBorderLeft(BorderStyle.getBorderStyleByName(borderLeft).value);
    }

    @Override
    public BorderStyle getBorderTop() {
        return BorderStyle.getBorderStyleByValue(this.getCellStyle().getBorderTop());
    }

    @Override
    public void setBorderTop(String borderTop) {
        this.getCellStyle().setBorderTop(BorderStyle.getBorderStyleByName(borderTop).value);
    }

    @Override
    public BorderStyle getBorderRight() {
        return BorderStyle.getBorderStyleByValue(this.getCellStyle().getBorderRight());
    }

    @Override
    public void setBorderRight(String borderRight) {
        this.getCellStyle().setBorderRight(BorderStyle.getBorderStyleByName(borderRight).value);
    }

    @Override
    public BorderStyle getBorderBottom() {
        return BorderStyle.getBorderStyleByValue(this.getCellStyle().getBorderBottom());
    }

    @Override
    public void setBorderBottom(String borderBottom) {
        this.getCellStyle().setBorderBottom(BorderStyle.getBorderStyleByName(borderBottom).value);
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
        return (XSSFCellStyle) this.cell.getCellStyle();
    }

    public void setCellStyle(XSSFCellStyle cellStyle) {
        this.cell.setCellStyle(cellStyle);
    }
}
