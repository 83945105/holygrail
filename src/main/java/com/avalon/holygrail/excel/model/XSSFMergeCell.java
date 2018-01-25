package com.avalon.holygrail.excel.model;

import com.avalon.holygrail.excel.norm.ExcelWorkBookExport;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

/**
 * XSSFWorkbook 合并单元格
 * Created by 白超 on 2018/1/24.
 */
public class XSSFMergeCell extends MergeCellAbstract {

    /**
     * 单元格合并信息
     */
    protected CellRangeAddress cellRangeAddress;
    /**
     * 数据助手
     */
    protected DataValidationHelper dataValidationHelper;

    protected CellStyle cellStyle;

    public XSSFMergeCell() {
    }

    public XSSFMergeCell(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    /**
     * 获取数据校验对象
     */
    public DataValidation getDataValidation() {
        if(this.getType() == CellType.COMBOBOX) {
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(cellRangeAddress.getFirstRow(), cellRangeAddress.getLastRow(), cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
            DataValidationConstraint constraint = dataValidationHelper.createExplicitListConstraint(this.getOptions());
            DataValidation dataValidation = dataValidationHelper.createValidation(constraint, cellRangeAddressList);
            if(dataValidation instanceof XSSFDataValidation) {
                dataValidation.setSuppressDropDownArrow(true);
                dataValidation.setShowErrorBox(true);
            }else {
                dataValidation.setSuppressDropDownArrow(true);
            }
            return dataValidation;
        }
        return null;
    }

    public CellRangeAddress getCellRangeAddress() {
        return cellRangeAddress;
    }

    public void setCellRangeAddress(CellRangeAddress cellRangeAddress) {
        this.cellRangeAddress = cellRangeAddress;
    }

    public DataValidationHelper getDataValidationHelper() {
        return dataValidationHelper;
    }

    public void setDataValidationHelper(DataValidationHelper dataValidationHelper) {
        this.dataValidationHelper = dataValidationHelper;
    }

    @Override
    public int getStartRow() {
        return this.cellRangeAddress.getFirstRow();
    }

    @Override
    public int getEndRow() {
        return this.cellRangeAddress.getLastRow();
    }

    @Override
    public int getStartCol() {
        return this.cellRangeAddress.getFirstColumn();
    }

    @Override
    public int getEndCol() {
        return this.cellRangeAddress.getLastColumn();
    }

    @Override
    public void readOnly(ExcelWorkBookExport.ReadOnly readOnly) {
        this.cellStyle.setLocked(readOnly.apply(this.cellStyle.getLocked()));
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }
}
