package com.avalon.holygrail.excel.norm;

/**
 * 合并单元格
 * Created by 白超 on 2018/1/16.
 */
public interface MergeCell {

    /**
     * 获取开始行号
     */
    Integer getStartRowNum();
    /**
     * 获取结束行号
     */
    Integer getEndRowNum();
    /**
     * 获取开始列号
     */
    Integer getStartColNum();
    /**
     * 获取结束列号
     * @return
     */
    Integer getEndColNum();

    /**
     * 设置开始行号
     * @param startRowNum 开始行号
     */
    void setStartRowNum(Integer startRowNum);

    /**
     * 设置开始列号
     * @param startColNum 开始列号
     */
    void setStartColNum(Integer startColNum);
}
