package com.avalon.holygrail.excel.norm;

/**
 * 合并单元格
 * Created by 白超 on 2018/1/16.
 */
public interface MergeCell {

    /**
     * @return 获取开始行号
     */
    int getStartRowNum();

    /**
     * @return 获取结束行号
     */
    int getEndRowNum();

    /**
     * @return 获取开始列号
     */
    int getStartColNum();

    /**
     * @return 获取结束列号
     */
    int getEndColNum();

    /**
     * 设置开始行号
     *
     * @param startRowNum 开始行号
     */
    void setStartRowNum(int startRowNum);

    /**
     * 设置开始列号
     *
     * @param startColNum 开始列号
     */
    void setStartColNum(int startColNum);
}
