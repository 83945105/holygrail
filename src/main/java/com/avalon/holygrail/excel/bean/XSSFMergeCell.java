package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.model.MergeCellAbstract;

/**
 * XSSFWorkbook 合并单元格
 * Created by 白超 on 2018/1/24.
 */
public class XSSFMergeCell extends MergeCellAbstract {

    public XSSFMergeCell(Integer startRow, Integer startCol) {
        super(startRow, startCol);
    }

    public XSSFMergeCell(Integer startRow, Integer startCol, Integer rowSpan, Integer colSpan) {
        super(startRow, startCol, rowSpan, colSpan);
    }

}
