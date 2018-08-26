package pub.avalon.holygrail.excel.norm;

import pub.avalon.holygrail.excel.exception.ExcelException;

/**
 * 单元格属性
 *
 * @author 白超
 * @date 2018/1/16
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
     * 获取单元格类型
     *
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
     * 获取单元格下拉框值
     *
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
     * 获取值
     *
     * @return
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
     * 获取映射值(属性)
     *
     * @return
     */
    String getField();

    /**
     * 设置映射值
     *
     * @param field
     */
    void setField(String field);

    /**
     * 获取单元格宽度
     *
     * @return
     */
    int getWidth();

    /**
     * 设置单元格宽度
     *
     * @param width
     */
    void setWidth(int width);

    /**
     * 获取占用多少行
     *
     * @return
     */
    int getRowSpan();

    /**
     * 设置占用多少行
     *
     * @param rowSpan
     */
    void setRowSpan(int rowSpan);

    /**
     * 获取占用多少列
     *
     * @return
     */
    int getColSpan();

    /**
     * 设置占用多少列
     *
     * @param colSpan
     */
    void setColSpan(int colSpan);

    /**
     * 是否写入空值
     *
     * @return
     */
    boolean isWriteEmpty();

    /**
     * 设置是否写入空值
     *
     * @param writeEmpty
     */
    void setWriteEmpty(boolean writeEmpty);

}
