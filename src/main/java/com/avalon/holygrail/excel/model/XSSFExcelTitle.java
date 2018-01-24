package com.avalon.holygrail.excel.model;

/**
 * XSSFWorkbook表头
 * Created by 白超 on 2018/1/24.
 */
public class XSSFExcelTitle extends ExcelTitleCellAbstract {

    public XSSFExcelTitle() {
    }

    public XSSFExcelTitle(String field) {
        super(field);
    }

    public XSSFExcelTitle(String title, String field) {
        super(title, field);
    }

    public XSSFExcelTitle(String title, String field, Integer rowSpan, Integer colSpan) {
        super(title, field, rowSpan, colSpan);
    }

    public XSSFExcelTitle(String title, String field, Integer width, Integer rowSpan, Integer colSpan, String HAlign, String VAlign) {
        super(title, field, width, rowSpan, colSpan, HAlign, VAlign);
    }
}
