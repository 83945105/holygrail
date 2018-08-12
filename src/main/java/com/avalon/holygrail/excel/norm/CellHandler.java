package com.avalon.holygrail.excel.norm;

/**
 * 单元格操作
 *
 * @author 白超
 * @date 2018-1-28
 */
public interface CellHandler {

    /**
     * 开始行号
     *
     * @return
     */
    int getStartRowNum();

    /**
     * 结束行号
     *
     * @return
     */
    int getEndRowNum();

    /**
     * 开始列号
     *
     * @return
     */
    int getStartColNum();

    /**
     * 结束列号
     *
     * @return
     */
    int getEndColNum();

    /**
     * 映射值
     *
     * @return
     */
    String getField();

    /**
     * 单元格类型
     *
     * @return
     */
    CellOption.CellType getCellType();

    /**
     * 单元格下拉框值
     *
     * @return
     */
    String[] getCellOptions();

    /**
     * 占用多少行
     *
     * @return
     */
    int getRowSpan();

    /**
     * 占用多少列
     *
     * @return
     */
    int getColSpan();

    /**
     * 是否写入空值
     *
     * @return
     */
    boolean isWriteEmpty();

    /**
     * 设置开始行
     *
     * @param startRowNum 开始行号
     */
    void setStartRowNum(int startRowNum);

    /**
     * 设置开始列
     *
     * @param startColNum 开始列号
     */
    void setStartColNum(int startColNum);

    /**
     * 设置映射值
     *
     * @param field
     */
    void setField(String field);

    /**
     * 设置单元格类型
     *
     * @param cellTypeString
     */
    void setCellType(String cellTypeString);

    /**
     * 设置单元格类型
     *
     * @param cellType
     */
    void setCellType(CellOption.CellType cellType);

    /**
     * 设置单元格类型
     *
     * @param cellTypeShort
     */
    void setCellType(short cellTypeShort);

    /**
     * 设置单元格下拉框值
     *
     * @param cellOptions
     */
    void setCellOptions(String[] cellOptions);

    /**
     * 设置占用多少行
     *
     * @param rowSpan
     */
    void setRowSpan(int rowSpan);

    /**
     * 设置占用多少列
     *
     * @param colSpan
     */
    void setColSpan(int colSpan);

    /**
     * 设置是否写入空值
     *
     * @param writeEmpty
     */
    void setWriteEmpty(boolean writeEmpty);

    /**
     * 获取单元格样式
     *
     * @return
     */
    CellStyle getCellStyle();

    /**
     * 设置单元格样式
     *
     * @param cellStyle
     */
    void setCellStyle(CellStyle cellStyle);

    /**
     * 是否是合并单元格
     *
     * @return
     */
    boolean isMergeCell();
}
