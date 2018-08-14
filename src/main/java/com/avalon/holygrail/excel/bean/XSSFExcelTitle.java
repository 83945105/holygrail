package com.avalon.holygrail.excel.bean;

/**
 * @author 白超
 * @date 2018/8/14
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
