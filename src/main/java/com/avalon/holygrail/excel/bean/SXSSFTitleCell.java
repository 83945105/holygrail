package com.avalon.holygrail.excel.bean;

/**
 * Created by 白超 on 2018/4/10.
 */
public class SXSSFTitleCell extends XSSFTitleCell {

    public SXSSFTitleCell() {
    }

    public SXSSFTitleCell(String field) {
        super(field);
    }

    public SXSSFTitleCell(String title, String field) {
        super(title, field);
    }

    public SXSSFTitleCell(int startRowNum, int startColNum) {
        super(startRowNum, startColNum);
    }

    public SXSSFTitleCell(int startRowNum, int startColNum, int rowSpan, int colSpan) {
        super(startRowNum, startColNum, rowSpan, colSpan);
    }
}
