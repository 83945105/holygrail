package com.avalon.holygrail.excel.bean;

/**
 * SXSSFWorkbook 合并单元格
 * Created by 白超 on 2018/1/17.
 */
public class SXSSFMergeCell extends XSSFMergeCell {

    public SXSSFMergeCell(Integer startRow, Integer startCol) {
        super(startRow, startCol);
    }

    public SXSSFMergeCell(Integer startRow, Integer startCol, Integer rowSpan, Integer colSpan) {
        super(startRow, startCol, rowSpan, colSpan);
    }

}
