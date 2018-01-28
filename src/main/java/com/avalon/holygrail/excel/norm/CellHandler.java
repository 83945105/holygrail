package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.exception.ExcelException;

/**
 * 单元格操作
 * Created by 白超 on 2018-1-28.
 */
public interface CellHandler {

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

    /**
     * 设置开始行
     * @param startRow 开始行号
     */
    void setStartRow(Integer startRow);

    /**
     * 设置开始列
     * @param startCol 开始列号
     */
    void setStartCol(Integer startCol);

    /**
     * 获取类型
     */
    CellOption.CellType getType();

    /**
     * 设置类型
     */
    void setType(String type);

    /**
     * 设置类型
     */
    void setType(CellOption.CellType type);

    /**
     * 设置类型
     */
    void setType(short value);

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
    Object getValue() throws ExcelException;

    /**
     * 设置值
     */
    void setValue(Object value);

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
     * 获取单元格水平对齐方式
     */
    CellStyle.H_AlignType getHAlign();

    /**
     * 设置单元格水平对齐方式
     */
    void setHAlign(String HAlign);

    /**
     * 设置单元格水平对齐方式
     */
    void setHAlign(CellStyle.H_AlignType hAlignType);

    /**
     * 设置单元格水平对齐方式
     */
    void setHAlign(short value);

    /**
     * 获取单元格垂直对齐方式
     */
    CellStyle.V_AlignType getVAlign();

    /**
     * 设置单元格垂直对齐方式
     */
    void setVAlign(String VAlign);

    /**
     * 设置单元格垂直对齐方式
     */
    void setVAlign(CellStyle.V_AlignType vAlignType);

    /**
     * 设置单元格垂直对齐方式
     */
    void setVAlign(short value);

    /**
     * 获取左边框样式
     */
    CellStyle.BorderStyle getBorderLeft();

    /**
     * 设置左边框样式
     */
    void setBorderLeft(String borderLeft);

    /**
     * 设置左边框样式
     */
    void setBorderLeft(CellStyle.BorderStyle borderStyle);

    /**
     * 设置左边框样式
     */
    void setBorderLeft(short value);

    /**
     * 获取上边框样式
     */
    CellStyle.BorderStyle getBorderTop();

    /**
     * 设置上边框样式
     */
    void setBorderTop(String borderTop);

    /**
     * 设置上边框样式
     */
    void setBorderTop(CellStyle.BorderStyle borderStyle);

    /**
     * 设置上边框样式
     */
    void setBorderTop(short value);

    /**
     * 获取右边框样式
     */
    CellStyle.BorderStyle getBorderRight();

    /**
     * 设置右边框样式
     */
    void setBorderRight(String borderRight);

    /**
     * 设置右边框样式
     */
    void setBorderRight(CellStyle.BorderStyle borderStyle);

    /**
     * 设置右边框样式
     */
    void setBorderRight(short value);

    /**
     * 获取下边框样式
     */
    CellStyle.BorderStyle getBorderBottom();

    /**
     * 设置下边框样式
     */
    void setBorderBottom(String borderBottom);

    /**
     * 设置下边框样式
     */
    void setBorderBottom(CellStyle.BorderStyle borderStyle);

    /**
     * 设置下边框样式
     */
    void setBorderBottom(short value);

    /**
     * 获取边框样式
     *
     * @return 左边框, 上边框, 右边框, 下边框
     */
    CellStyle.BorderStyle[] getBorder();

    /**
     * 设置四个边框样式,以","分隔,顺序为左上右下
     */
    void setBorder(String border);

    /**
     * 设置四个边框样式,顺序为左上右下
     */
    void setBorder(CellStyle.BorderStyle[] borderStyles);

    /**
     * 设置四个边框样式,顺序为左上右下
     */
    void setBorder(short[] values);
}
