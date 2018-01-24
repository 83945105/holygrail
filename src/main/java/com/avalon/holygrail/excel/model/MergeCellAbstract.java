package com.avalon.holygrail.excel.model;

import com.avalon.holygrail.excel.norm.CellOption;
import com.avalon.holygrail.excel.norm.CellStyle;
import com.avalon.holygrail.excel.norm.MergeCell;

/**
 * 合并单元格属性
 * Created by 白超 on 2018/1/17.
 */
public abstract class MergeCellAbstract extends ExcelCellAbstract implements MergeCell, CellOption, CellStyle {

    /**
     * 垂直对齐方式-默认顶部对齐
     */
    protected String VAlign = "center";

    @Override
    public Integer getRowSpan() {
        return this.getEndRow() - this.getStartRow() + 1;
    }

    @Override
    public Integer getColSpan() {
        return this.getEndCol() - this.getStartCol() + 1;
    }

    @Override
    public V_AlignType getVAlign() {
        return V_AlignType.getVAlignByName(VAlign);
    }

    @Override
    public void setVAlign(String VAlign) {
        this.VAlign = VAlign.toLowerCase();
    }

}
