package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.bean.SXSSFExcelSheet;
import com.avalon.holygrail.excel.exception.ExcelTitleException;
import com.avalon.holygrail.excel.exception.ExportException;
import com.avalon.holygrail.excel.model.ExcelTitleAbstract;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Excel 工作表
 * Created by 白超 on 2018/1/17.
 */
public interface ExcelSheet extends Sheet {

    /**
     * 获取所属Excel工作簿
     * @return Excel工作簿
     */
    ExcelWorkBook getOwnerWorkBook();

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
    ExcelSheet setRowCursor(Function<Integer, Integer> handler);

    @Override
    ExcelSheet setColCursor(Function<Integer, Integer> handler);

    @Override
    ExcelSheet parseTitlesJson(String titlesJson, boolean exportTitles) throws ExcelTitleException, ExportException;

    @Override
    ExcelSheet parseTitlesJson(InputStream inputStream, boolean exportTitles) throws IOException, ExcelTitleException, ExportException;

    @Override
    ExcelSheet parseTitlesJson(File file, boolean exportTitles) throws IOException, ExcelTitleException, ExportException;

    @Override
    ExcelSheet setTitles(ExcelTitleAbstract[][] titles, boolean exportTitles) throws ExcelTitleException, ExportException;

    @Override
    ExcelSheet setColumnFields(List<String> fields) throws ExcelTitleException, ExportException;

    @Override
    default ExcelSheet setColumnFields(String... fields) throws ExcelTitleException, ExportException {
        return setColumnFields(Arrays.asList(fields));
    }

    @Override
    default ExcelSheet parseTitlesJson(InputStream inputStream) throws IOException, ExcelTitleException, ExportException {
        return parseTitlesJson(inputStream, true);
    }

    @Override
    default ExcelSheet parseTitlesJson(File file) throws IOException, ExcelTitleException, ExportException {
        return parseTitlesJson(file, true);
    }

    @Override
    default ExcelSheet parseTitlesJson(String titlesJson) throws ExcelTitleException, ExportException {
        return parseTitlesJson(titlesJson, true);
    }

    @Override
    default ExcelSheet setTitles(ExcelTitleAbstract[][] titles) throws ExcelTitleException, ExportException {
        return setTitles(titles, true);
    }

    @Override
    <T> ExcelSheet importData(Collection<T> records) throws ExportException;

    @Override
    <T> ExcelSheet importData(Collection<T> records, SXSSFExcelSheet.FormatterCell<T> formatter) throws ExportException;

}
