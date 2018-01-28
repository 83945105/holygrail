package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.bean.SXSSFExcelSheetExport;
import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.model.ExcelTitleCellAbstract;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Excel Sheet导出
 * Created by 白超 on 2018/1/17.
 */
public interface ExcelSheetExport extends SheetExportHandler {

    @Override
    ExcelSheetExport setRowCursor(Function<Integer, Integer> handler);

    @Override
    ExcelSheetExport setColCursor(Function<Integer, Integer> handler);

    @Override
    ExcelSheetExport parseTitlesJson(String titlesJson, boolean exportTitles) throws ExcelException;

    @Override
    ExcelSheetExport parseTitlesJson(InputStream inputStream, boolean exportTitles) throws IOException, ExcelException;

    @Override
    ExcelSheetExport parseTitlesJson(File file, boolean exportTitles) throws IOException, ExcelException;

    @Override
    ExcelSheetExport setTitles(ExcelTitleCellAbstract[][] titles, boolean exportTitles) throws ExcelException;

    @Override
    ExcelSheetExport setColumnFields(List<String> fields) throws ExcelException;

    @Override
    default ExcelSheetExport setColumnFields(String... fields) throws ExcelException {
        return setColumnFields(Arrays.asList(fields));
    }

    @Override
    default ExcelSheetExport parseTitlesJson(InputStream inputStream) throws IOException, ExcelException {
        return parseTitlesJson(inputStream, true);
    }

    @Override
    default ExcelSheetExport parseTitlesJson(File file) throws IOException, ExcelException {
        return parseTitlesJson(file, true);
    }

    @Override
    default ExcelSheetExport parseTitlesJson(String titlesJson) throws ExcelException {
        return parseTitlesJson(titlesJson, true);
    }

    @Override
    default ExcelSheetExport setTitles(ExcelTitleCellAbstract[][] titles) throws ExcelException {
        return setTitles(titles, true);
    }

    /**
     * 导入数据
     * @param records 数据集合
     * @param <T> 数据类型
     * @return 当前对象
     */
    <T> ExcelSheetExport importData(Collection<T> records) throws ExcelException;

    /**
     * 导入数据
     * @param records 数据集合
     * @param <T> 数据类型
     * @param formatter 格式化函数,接收5个参数,分别为 当前当前数据对象record、单元格信息、当前列值、游标、当前记录下标,需要返回要设置的单元格值
     * @return 当前对象
     */
    <T> ExcelSheetExport importData(Collection<T> records, SXSSFExcelSheetExport.FormatterCell<T> formatter) throws ExcelException;

    /**
     * 导出Excel
     * @param outFile 目标文件
     * @return 当前对象
     * @throws IOException
     */
    void export(File outFile) throws IOException;

    /**
     * 导出Excel
     * @param outPath 导出地址
     * @return 当前对象
     * @throws IOException
     */
    void export(String outPath) throws IOException;

    @Override
    default ExcelSheetExport setRowNum(Function<Integer, Integer> handler) {
        return this.setRowCursor(rowCursor -> handler.apply(rowCursor + 1) - 1);
    }

    @Override
    default ExcelSheetExport setColumnNum(Function<Integer, Integer> handler) {
        return this.setColCursor(colCursor -> handler.apply(colCursor + 1) - 1);
    }

}
