package com.avalon.holygrail.excel.norm;

/**
 * 合并单元格
 * Created by 白超 on 2018/1/16.
 */
public interface MergeCell {

    /**
     * 获取开始行
     */
    Integer getStartRow();
    /**
     * 获取结束行
     */
    Integer getEndRow();
    /**
     * 获取开始列
     */
    Integer getStartCol();
    /**
     * 获取结束列
     * @return
     */
    Integer getEndCol();

    /**
     * 设置开始行
     * @param startRow 开始行号
     */
    void setStartRow(Integer startRow);

    /**
     * 设置开始列
     * @param startCol 开始列号
     */
    void setStartCol(Integer startCol);
}
