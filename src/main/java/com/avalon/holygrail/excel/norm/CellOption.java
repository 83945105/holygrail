package com.avalon.holygrail.excel.norm;

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
     * 获取类型
     */
    CellType getType();

    /**
     * 设置类型
     */
    void setType(String type);

    /**
     * 设置类型
     */
    default void setType(CellType type) {
        this.setType(type.name());
    }

    /**
     * 获取下拉框值
     */
    String[] getOptions();

    /**
     * 设置下拉框值
     */
    void setOptions(String[] options);

    /**
     * 获取值
     */
    Object getValue();

    /**
     * 设置值
     */
    void setValue(Object value);

    /**
     * 获取字段名称
     */
    String getField();

    /**
     * 设置字段名称
     */
    void setField(String field);

    /**
     * 获取宽
     */
    Integer getWidth();

    /**
     * 设置宽
     */
    void setWidth(Integer width);

    /**
     * 获取占用多少行
     */
    Integer getRowSpan();

    /**
     * 设置占用多少行
     */
    void setRowSpan(Integer rowSpan);

    /**
     * 获取占用多少列
     */
    Integer getColSpan();

    /**
     * 设置占用多少列
     */
    void setColSpan(Integer colSpan);

    /**
     * 拷贝属性
     *
     * @param target 目标单元格
     */
    default void copyCellOption(CellOption target) {
        target.setType(getType().name());
        target.setValue(getValue());
        target.setField(getField());
        target.setWidth(getWidth());
        target.setRowSpan(getRowSpan());
        target.setColSpan(getColSpan());
    }
}
