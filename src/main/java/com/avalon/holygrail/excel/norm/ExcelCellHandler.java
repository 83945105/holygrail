package com.avalon.holygrail.excel.norm;

/**
 * Created by 白超 on 2018/4/10.
 */
public interface ExcelCellHandler extends CellHandler {

    /**
     * @return 单元格值
     */
    Object getValue();

    /**
     * 设置值
     *
     * @param value
     */
    void setValue(Object value);

    /**
     * 获取单元格样式
     *
     * @param index 已经创建的样式下标
     * @return
     */
    CellStyle findCellStyle(int index);

    /**
     * 获取字体
     *
     * @param index 已经创建的字体下标
     * @return
     */
    Font findFont(int index);

}
