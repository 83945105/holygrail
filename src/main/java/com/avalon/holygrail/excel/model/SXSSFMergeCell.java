package com.avalon.holygrail.excel.model;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * SXSSFWorkbook 合并单元格
 * Created by 白超 on 2018/1/17.
 */
public class SXSSFMergeCell extends XSSFMergeCell {

    public SXSSFMergeCell() {
    }

    public SXSSFMergeCell(CellStyle cellStyle) {
        super(cellStyle);
    }
}
