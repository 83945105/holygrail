package com.avalon.holygrail.excel.bean;

/**
 * SXSSFWorkbook 表头
 *
 * @author 白超
 * @date 2018/1/16
 */
public class SXSSFExcelTitle extends XSSFExcelTitle {

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

    public SXSSFExcelTitle(String title, String field, int rowSpan, int colSpan) {
        super(title, field, rowSpan, colSpan);
    }
}
