package com.avalon.holygrail.excel.norm;

/**
 * Created by 白超 on 2018/4/10.
 */
public interface ExcelTitleCellHandler extends CellHandler {

    /**
     * 获取标题文本
     *
     * @return
     */
    String getTitle();

    /**
     * 设置标题文本
     *
     * @param title
     */
    void setTitle(String title);

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
}
