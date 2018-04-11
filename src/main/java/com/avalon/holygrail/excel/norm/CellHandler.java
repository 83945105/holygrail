package com.avalon.holygrail.excel.norm;

/**
 * 单元格操作
 * Created by 白超 on 2018-1-28.
 */
public interface CellHandler {

    /********************************GET********************************/

    /*-----------------------------OPTIONS-----------------------------*/

    /**
     * @return 开始行号
     */
    int getStartRowNum();

    /**
     * @return 结束行号
     */
    int getEndRowNum();

    /**
     * @return 开始列号
     */
    int getStartColNum();

    /**
     * @return 结束列号
     */
    int getEndColNum();

    /**
     * @return 映射值
     */
    String getField();

    /**
     * @return 单元格类型
     */
    CellOption.CellType getCellType();

    /**
     * @return 单元格下拉框值
     */
    String[] getCellOptions();

    /**
     * @return 占用多少行
     */
    int getRowSpan();

    /**
     * @return 占用多少列
     */
    int getColSpan();

    /**
     * @return 是否写入空值
     */
    boolean isWriteEmpty();

    /*-----------------------------OPTIONS-----------------------------*/

    /********************************GET********************************/

    /********************************SET********************************/

    /*-----------------------------OPTIONS-----------------------------*/

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

    /*-----------------------------OPTIONS-----------------------------*/


    /********************************SET********************************/

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
