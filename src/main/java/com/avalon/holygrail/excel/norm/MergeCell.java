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

}
