package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.model.BaseExcelTitleCell;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * Excel Sheet导入
 *
 * @author 白超
 * @date 2018/1/24
 */
public interface ExcelSheetImport extends SheetImportHandler {

    @Override
    ExcelSheetImport setRowCursor(Function<Integer, Integer> handler);

    @Override
    ExcelSheetImport setColCursor(Function<Integer, Integer> handler);

    @Override
    default ExcelSheetImport setRowNum(Function<Integer, Integer> handler) {
        return this.setRowCursor(rowCursor -> handler.apply(rowCursor + 1) - 1);
    }

    @Override
    default ExcelSheetImport setColumnNum(Function<Integer, Integer> handler) {
        return this.setColCursor(colCursor -> handler.apply(colCursor + 1) - 1);
    }

    @Override
    <T> ExcelSheetImport parseTitlesJson(String titlesJson, Class<T> clazz) throws ExcelException;

    @Override
    <T> ExcelSheetImport parseTitlesJson(InputStream inputStream, Class<T> clazz) throws IOException, ExcelException;

    @Override
    <T> ExcelSheetImport parseTitlesJson(File file, Class<T> clazz) throws IOException, ExcelException;

    @Override
    <T> ExcelSheetImport setTitles(BaseExcelTitleCell[][] titles, Class<T> clazz) throws ExcelException;

    @Override
    <T> ExcelSheetImport setColumnFields(List<String> fields, Class<T> clazz) throws ExcelException;

    @Override
    <T> ExcelSheetImport setColumnFields(int rowSpan, List<String> fields, Class<T> clazz) throws ExcelException;

    @Override
    default ExcelSheetImport setColumnFields(String... fields) throws ExcelException {
        return setColumnFields(Arrays.asList(fields), HashMap.class);
    }

    @Override
    default ExcelSheetImport setColumnFields(int rowSpan, String... fields) throws ExcelException {
        return setColumnFields(rowSpan, Arrays.asList(fields), HashMap.class);
    }

    @Override
    <T> ExcelSheetImport readRows(Class<T> clazz) throws ExcelException, InstantiationException, IllegalAccessException;

    @Override
    <T> ExcelSheetImport readRows(Class<T> clazz, HandlerRowA<T> handlerRow) throws ExcelException, InstantiationException, IllegalAccessException;

    @Override
    <T> ExcelSheetImport readRows(Class<T> clazz, HandlerRowB<T> handlerRow) throws ExcelException, InstantiationException, IllegalAccessException;

    @Override
    ExcelSheetImport readRows() throws ExcelException, IllegalAccessException, InstantiationException;

    @Override
    <T> ExcelSheetImport readRows(HandlerRowA<T> handlerRow) throws ExcelException, IllegalAccessException, InstantiationException;

    @Override
    <T> ExcelSheetImport readRows(HandlerRowB<T> handlerRow) throws ExcelException, IllegalAccessException, InstantiationException;
}
