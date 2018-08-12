package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.model.BaseExcelTitleCell;

/**
 * XSSFWorkbook表头
 *
 * @author 白超
 * @date 2018/1/24
 */
public class XSSFExcelTitle extends BaseExcelTitleCell {

    public XSSFExcelTitle() {
    }

    public XSSFExcelTitle(String field) {
        super(field);
    }

    public XSSFExcelTitle(String title, String field) {
        super(title, field);
    }

    public XSSFExcelTitle(int startRowNum, int startColNum) {
        super(startRowNum, startColNum);
    }

    public XSSFExcelTitle(int startRowNum, int startColNum, int rowSpan, int colSpan) {
        super(startRowNum, startColNum, rowSpan, colSpan);
    }

    public XSSFExcelTitle(String title, String field, int rowSpan, int colSpan) {
        super(title, field, rowSpan, colSpan);
    }
}
