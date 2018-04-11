package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.model.BaseExcelTitleCell;

/**
 * Created by 白超 on 2018/4/10.
 */
public class XSSFTitleCell extends BaseExcelTitleCell {

    public XSSFTitleCell() {
    }

    public XSSFTitleCell(String field) {
        super(field);
    }

    public XSSFTitleCell(String title, String field) {
        super(title, field);
    }

    public XSSFTitleCell(int startRowNum, int startColNum) {
        super(startRowNum, startColNum);
    }

    public XSSFTitleCell(int startRowNum, int startColNum, int rowSpan, int colSpan) {
        super(startRowNum, startColNum, rowSpan, colSpan);
    }
}
