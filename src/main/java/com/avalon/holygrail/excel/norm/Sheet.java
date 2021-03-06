package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.model.ExcelTitleCellAbstract;
import com.avalon.holygrail.util.AlgorithmicUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

/**
 * 工作表
 * Created by 白超 on 2018/1/24.
 */
public interface Sheet {

    /**
     * 获取所属Excel工作簿
     *
     * @return Excel工作簿
     */
    ExcelWorkBook getOwnerWorkBook();

    /**
     * 设置行游标
     *
     * @param handler 接收行号,返回你想设置的行号
     * @return
     */
    Sheet setRowCursor(Function<Integer, Integer> handler);

    /**
     * 设置列游标
     *
     * @param handler 接收列号,返回你想设置的列号
     * @return
     */
    Sheet setColCursor(Function<Integer, Integer> handler);

    /**
     * 解析表头json文件
     *
     * @param inputStream 表头数据流
     * @return 准备导出
     */
    Sheet parseTitlesJson(InputStream inputStream) throws IOException, ExcelException;

    /**
     * 解析表头json文件
     *
     * @param file 表头数据文件
     * @return 准备导出
     */
    Sheet parseTitlesJson(File file) throws IOException, ExcelException;

    /**
     * 解析表头json数据
     *
     * @param titlesJson 表头数据json
     * @return 准备导出
     */
    Sheet parseTitlesJson(String titlesJson) throws ExcelException;

    /**
     * 设置表头
     *
     * @param titles 表头对象
     * @return 准备导出
     */
    Sheet setTitles(ExcelTitleCellAbstract[][] titles) throws ExcelException;

    /**
     * 设置行号
     *
     * @param handler 接收当前行号,返回你想设置的行号
     * @return
     */
    default Sheet setRowNum(Function<Integer, Integer> handler) {
        return this.setRowCursor(rowCursor -> handler.apply(rowCursor + 1) - 1);
    }

    /**
     * 设置列号
     *
     * @param handler 接收当前列号,返回你想设置的列号
     * @return
     */
    default Sheet setColumnNum(Function<Integer, Integer> handler) {
        return this.setColCursor(colCursor -> handler.apply(colCursor + 1) - 1);
    }

    /**
     * 工作表列名
     */
    String[] SheetColumnNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 获取表格列名
     *
     * @param columnNum 列号
     * @return 列名
     */
    static String getColumnName(int columnNum) {
        return AlgorithmicUtil.calculateStrByNumber(columnNum, SheetColumnNames);
    }

    /**
     * 获取表格列号
     * @param columnName 列名
     * @return 列号
     */
    static Integer getColumnNum(String columnName) {
        return AlgorithmicUtil.calculateNumByString(columnName, SheetColumnNames).intValue();
    }

}
