package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.exception.ExcelTitleException;
import com.avalon.holygrail.excel.exception.ExportException;
import com.avalon.holygrail.excel.model.ExcelTitleCellAbstract;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Excel Sheet导入
 * Created by 白超 on 2018/1/24.
 */
public interface ExcelSheetImport extends SheetImportHandler {

    @Override
    ExcelSheetImport setRowCursor(Function<Integer, Integer> handler);

    @Override
    ExcelSheetImport setColCursor(Function<Integer, Integer> handler);

    @Override
    <T> ExcelSheetImport parseTitlesJson(String titlesJson, Class<T> clazz) throws ExcelException;

    @Override
    <T> ExcelSheetImport parseTitlesJson(InputStream inputStream, Class<T> clazz) throws IOException, ExcelException;

    @Override
    <T> ExcelSheetImport parseTitlesJson(File file, Class<T> clazz) throws IOException, ExcelException;

    @Override
    <T> ExcelSheetImport setTitles(ExcelTitleCellAbstract[][] titles, Class<T> clazz) throws ExcelException;

    @Override
    <T> ExcelSheetImport setColumnFields(List<String> fields, Class<T> clazz) throws ExcelException;

    @Override
    default ExcelSheetImport setColumnFields(String... fields) throws ExcelException {
        return setColumnFields(Arrays.asList(fields), Map.class);
    }

    /**
     * 获取所属Excel工作簿
     * @return Excel工作簿
     */
    ExcelWorkBookImport getOwnerWorkBook();
}
