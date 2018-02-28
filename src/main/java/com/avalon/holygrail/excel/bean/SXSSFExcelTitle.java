package com.avalon.holygrail.excel.bean;

/**
 * SXSSFWorkbook 表头
 * Created by 白超 on 2018/1/16.
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

    public SXSSFExcelTitle(String title, String field, Integer rowSpan, Integer colSpan) {
        super(title, field, rowSpan, colSpan);
    }

    public SXSSFExcelTitle(String title, String field, Integer width, Integer rowSpan, Integer colSpan, String HAlign, String VAlign) {
        super(title, field, width, rowSpan, colSpan, HAlign, VAlign);
    }
}
