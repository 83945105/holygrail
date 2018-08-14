package com.avalon.holygrail.excel.bean;

/**
 * @author 白超
 * @date 2018/8/14
 */
public class SXSSFExcelTitle extends SXSSFTitleCell {

    public SXSSFExcelTitle() {
    }

    public SXSSFExcelTitle(String field) {
        super(field);
    }

    public SXSSFExcelTitle(String title, String field) {
        super(title, field);
    }

    public SXSSFExcelTitle(int startRowNum, int startColNum) {
        super(startRowNum, startColNum);
    }

    public SXSSFExcelTitle(int startRowNum, int startColNum, int rowSpan, int colSpan) {
        super(startRowNum, startColNum, rowSpan, colSpan);
    }
}
