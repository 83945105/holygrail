package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.bean.SXSSFExcelExportSheet;
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
 * Excel Sheet导出
 * Created by 白超 on 2018/1/17.
 */
public interface ExcelSheetExport extends SheetExport {

    /**
     * 获取所属Excel工作簿
     * @return Excel工作簿
     */
    ExcelExportWorkBook getOwnerWorkBook();

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
    ExcelSheetExport setRowCursor(Function<Integer, Integer> handler);

    @Override
    ExcelSheetExport setColCursor(Function<Integer, Integer> handler);

    @Override
    ExcelSheetExport parseTitlesJson(String titlesJson, boolean exportTitles) throws ExcelTitleException, ExportException;

    @Override
    ExcelSheetExport parseTitlesJson(InputStream inputStream, boolean exportTitles) throws IOException, ExcelTitleException, ExportException;

    @Override
    ExcelSheetExport parseTitlesJson(File file, boolean exportTitles) throws IOException, ExcelTitleException, ExportException;

    @Override
    ExcelSheetExport setTitles(ExcelTitleAbstract[][] titles, boolean exportTitles) throws ExcelTitleException, ExportException;

    @Override
    ExcelSheetExport setColumnFields(List<String> fields) throws ExcelTitleException, ExportException;

    @Override
    default ExcelSheetExport setColumnFields(String... fields) throws ExcelTitleException, ExportException {
        return setColumnFields(Arrays.asList(fields));
    }

    @Override
    default ExcelSheetExport parseTitlesJson(InputStream inputStream) throws IOException, ExcelTitleException, ExportException {
        return parseTitlesJson(inputStream, true);
    }

    @Override
    default ExcelSheetExport parseTitlesJson(File file) throws IOException, ExcelTitleException, ExportException {
        return parseTitlesJson(file, true);
    }

    @Override
    default ExcelSheetExport parseTitlesJson(String titlesJson) throws ExcelTitleException, ExportException {
        return parseTitlesJson(titlesJson, true);
    }

    @Override
    default ExcelSheetExport setTitles(ExcelTitleAbstract[][] titles) throws ExcelTitleException, ExportException {
        return setTitles(titles, true);
    }

    @Override
    <T> ExcelSheetExport importData(Collection<T> records) throws ExportException;

    @Override
    <T> ExcelSheetExport importData(Collection<T> records, SXSSFExcelExportSheet.FormatterCell<T> formatter) throws ExportException;

}
