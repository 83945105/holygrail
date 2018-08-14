package com.avalon.holygrail.excel.bean;

/**
 * XSSFWorkbook表头
 * Created by 白超 on 2018/1/24.
 */
public class XSSFExcelTitle extends XSSFTitleCell {

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
}
