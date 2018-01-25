package com.avalon.holygrail.excel.norm;

/**
 * 合并单元格
 * Created by 白超 on 2018/1/16.
 */
public interface MergeCell {

    /**
     * 获取开始行
     */
    int getStartRow();
    /**
     * 获取结束行
     */
    int getEndRow();
    /**
     * 获取开始列
     */
    int getStartCol();
    /**
     * 获取结束列
     * @return
     */
    int getEndCol();

    /**
     * 只读
     * @return 操作只读状态
     */
    void readOnly(ExcelWorkBookExport.ReadOnly readOnly);
}
