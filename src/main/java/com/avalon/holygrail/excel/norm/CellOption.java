package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.exception.ExcelException;

/**
 * 单元格属性
 * Created by 白超 on 2018/1/16.
 */
public interface CellOption {

    /**
     * 单元格类型
     */
    enum CellType {
        /**
         * 文本框
         */
        TEXT((short) 0),
        /**
         * 下拉框
         */
        COMBOBOX((short) 1);

        public short value;

        CellType(short value) {
            this.value = value;
        }

        /**
         * 根据name获取单元格类型
         */
        public static CellType getCellTypeByName(String name) {
            for (CellType cellType : CellType.values()) {
                if (cellType.name().equalsIgnoreCase(name)) {
                    return cellType;
                }
            }
            throw new RuntimeException("cellType值不正确:" + name);
        }

        /**
         * 根据value获取单元格类型
         */
        public static CellType getCellTypeByValue(short value) {
            for (CellType cellType : CellType.values()) {
                if (cellType.value == value) {
                    return cellType;
                }
            }
            throw new RuntimeException("cellType值不正确:" + value);
        }
    }

    /**
     * @return 单元格类型
     */
    CellType getCellType();

    /**
     * 设置单元格类型
     *
     * @param cellType
     */
    void setCellType(CellType cellType);

    /**
     * 设置单元格类型
     *
     * @param cellTypeString
     */
    default void setCellType(String cellTypeString) {
        this.setCellType(CellType.getCellTypeByName(cellTypeString));
    }

    /**
     * 设置单元格类型
     *
     * @param cellTypeShort
     */
    default void setCellType(short cellTypeShort) {
        this.setCellType(CellType.getCellTypeByValue(cellTypeShort));
    }

    /**
     * @return 下拉框值
     */
    String[] getCellOptions();

    /**
     * 设置下拉框值
     *
     * @param cellOptions
     */
    void setCellOptions(String[] cellOptions);

    /**
     * @return 获取值
     * @throws ExcelException
     */
    Object getValue() throws ExcelException;

    /**
     * 设置值
     *
     * @param value
     */
    void setValue(Object value);

    /**
     * @return 映射值
     */
    String getField();

    /**
     * 设置映射值
     *
     * @param field
     */
    void setField(String field);

    /**
     * @return 单元格宽度
     */
    int getWidth();

    /**
     * 设置单元格宽度
     *
     * @param width
     */
    void setWidth(int width);

    /**
     * @return 占用多少行
     */
    int getRowSpan();

    /**
     * 设置占用多少行
     *
     * @param rowSpan
     */
    void setRowSpan(int rowSpan);

    /**
     * @return 占用多少列
     */
    int getColSpan();

    /**
     * 设置占用多少列
     *
     * @param colSpan
     */
    void setColSpan(int colSpan);

    /**
     * @return 是否写入空值
     */
    boolean isWriteEmpty();

    /**
     * 设置是否写入空值
     *
     * @param writeEmpty
     */
    void setWriteEmpty(boolean writeEmpty);

}
