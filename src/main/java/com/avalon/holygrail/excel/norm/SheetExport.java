package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.bean.SXSSFExcelExportSheet;
import com.avalon.holygrail.excel.exception.ExcelTitleException;
import com.avalon.holygrail.excel.exception.ExportException;
import com.avalon.holygrail.excel.model.ExcelTitleCellAbstract;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Sheet导出
 * Created by 白超 on 2018/1/19.
 */
public interface SheetExport {

    /**
     * 设置行游标
     * @param handler 接收行号,返回你想设置的行号
     */
    SheetExport setRowCursor(Function<Integer, Integer> handler);

    /**
     * 设置列游标
     * @param handler 接收列号,返回你想设置的列号
     */
    SheetExport setColCursor(Function<Integer, Integer> handler);

    /**
     * 解析表头json数据
     * @param titlesJson 表头数据json
     * @param exportTitles 是否导出表头
     * @return 准备导出
     */
    SheetExport parseTitlesJson(String titlesJson, boolean exportTitles) throws ExcelTitleException, ExportException;

    /**
     * 解析表头json文件
     * @param inputStream 表头数据流
     * @param exportTitles 是否导出表头
     * @return 准备导出
     */
    SheetExport parseTitlesJson(InputStream inputStream, boolean exportTitles) throws IOException, ExcelTitleException, ExportException;

    /**
     * 解析表头json文件
     * @param file 表头数据文件
     * @param exportTitles 是否导出表头
     * @return 准备导出
     */
    SheetExport parseTitlesJson(File file, boolean exportTitles) throws IOException, ExcelTitleException, ExportException;

    /**
     * 设置表头
     * @param titles 表头对象
     * @param exportTitles 是否导出表头
     * @return 准备导出
     */
    SheetExport setTitles(ExcelTitleCellAbstract[][] titles, boolean exportTitles) throws ExcelTitleException, ExportException;

    /**
     * 设置列属性
     * @param fields 属性
     * @return 准备导出
     */
    SheetExport setColumnFields(List<String> fields) throws ExcelTitleException, ExportException;

    /**
     * 设置列宽
     * @param columnIndex 列号
     * @param width 列宽
     */
    void setColumnWidth(int columnIndex, int width);

    /**
     * 设置列对应的数据属性
     * @param fields 属性
     * @return 准备导出
     */
    default SheetExport setColumnFields(String... fields) throws ExcelTitleException, ExportException {
        return setColumnFields(Arrays.asList(fields));
    }

    /**
     * 解析表头json文件
     * @param inputStream 表头数据流
     * @return 准备导出
     */
    default SheetExport parseTitlesJson(InputStream inputStream) throws IOException, ExcelTitleException, ExportException {
        return parseTitlesJson(inputStream, true);
    }

    /**
     * 解析表头json文件
     * @param file 表头数据文件
     * @return 准备导出
     */
    default SheetExport parseTitlesJson(File file) throws IOException, ExcelTitleException, ExportException {
        return parseTitlesJson(file, true);
    }

    /**
     * 解析表头json数据
     * @param titlesJson 表头数据json
     * @return 准备导出
     */
    default SheetExport parseTitlesJson(String titlesJson) throws ExcelTitleException, ExportException {
        return parseTitlesJson(titlesJson, true);
    }

    /**
     * 设置表头
     * @param titles 表头对象
     * @return 准备导出
     */
    default SheetExport setTitles(ExcelTitleCellAbstract[][] titles) throws ExcelTitleException, ExportException {
        return setTitles(titles, true);
    }

    /**
     * 导入数据
     * @param records 数据集合
     * @param <T> 数据类型
     * @return 当前对象
     */
    <T> SheetExport importData(Collection<T> records) throws ExportException;

    /**
     * 导入数据
     * @param records 数据集合
     * @param <T> 数据类型
     * @param formatter 格式化函数,接收5个参数,分别为 当前当前数据对象record、单元格信息、当前列值、游标、当前记录下标,需要返回要设置的单元格值
     * @return 当前对象
     */
    <T> SheetExport importData(Collection<T> records, SXSSFExcelExportSheet.FormatterCell<T> formatter) throws ExportException;

    /**
     * 获取表格导入的数据总数
     * @return 数据总数
     */
    int getTotalDataSize();
}
