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

    /**
     * 设置行游标
     *
     * @param handler 接收行号,返回你想设置的行号
     * @return ExcelSheetImport
     */
    @Override
    ExcelSheetImport setRowCursor(Function<Integer, Integer> handler);

    /**
     * 设置列游标
     *
     * @param handler 接收列号,返回你想设置的列号
     * @return ExcelSheetImport
     */
    @Override
    ExcelSheetImport setColCursor(Function<Integer, Integer> handler);

    /**
     * 设置行号
     *
     * @param handler 接收当前行号,返回你想设置的行号
     * @return ExcelSheetImport
     */
    @Override
    default ExcelSheetImport setRowNum(Function<Integer, Integer> handler) {
        return this.setRowCursor(rowCursor -> handler.apply(rowCursor + 1) - 1);
    }

    /**
     * 设置列号
     *
     * @param handler 接收当前列号,返回你想设置的列号
     * @return ExcelSheetImport
     */
    @Override
    default ExcelSheetImport setColumnNum(Function<Integer, Integer> handler) {
        return this.setColCursor(colCursor -> handler.apply(colCursor + 1) - 1);
    }

    /**
     * 解析表头Json数据
     *
     * @param titlesJson 表头数据json
     * @param clazz      数据容器
     * @param <T>        容器类型
     * @return ExcelSheetImport
     * @throws ExcelException 参考实现
     */
    @Override
    <T> ExcelSheetImport parseTitlesJson(String titlesJson, Class<T> clazz) throws ExcelException;

    /**
     * 解析表头数据
     *
     * @param inputStream 表头数据流
     * @param clazz       数据容器
     * @param <T>         容器类型
     * @return ExcelSheetImport
     * @throws IOException    参考实现
     * @throws ExcelException 参考实现
     */
    @Override
    <T> ExcelSheetImport parseTitlesJson(InputStream inputStream, Class<T> clazz) throws IOException, ExcelException;

    /**
     * 解析表头文件
     *
     * @param file  表头数据文件
     * @param clazz 数据容器
     * @param <T>   容器类型
     * @return ExcelSheetImport
     * @throws IOException    参考实现
     * @throws ExcelException 参考实现
     */
    @Override
    <T> ExcelSheetImport parseTitlesJson(File file, Class<T> clazz) throws IOException, ExcelException;

    /**
     * 设置表头
     *
     * @param titles 表头对象
     * @param clazz  数据容器
     * @param <T>    容器类型
     * @return ExcelSheetImport
     * @throws ExcelException titles类型不正确
     */
    @Override
    <T> ExcelSheetImport setTitles(BaseExcelTitleCell[][] titles, Class<T> clazz) throws ExcelException;

    /**
     * 设置列对应的数据属性
     *
     * @param fields 属性
     * @param clazz  数据容器
     * @param <T>    容器类型
     * @return ExcelSheetImport
     * @throws ExcelException 参考实现
     */
    @Override
    <T> ExcelSheetImport setColumnFields(List<String> fields, Class<T> clazz) throws ExcelException;

    /**
     * 设置列对应的数据属性
     *
     * @param rowSpan 占用行数
     * @param fields  属性
     * @param clazz   数据容器
     * @param <T>     容器类型
     * @return ExcelSheetImport
     * @throws ExcelException 参考实现
     */
    @Override
    <T> ExcelSheetImport setColumnFields(int rowSpan, List<String> fields, Class<T> clazz) throws ExcelException;

    /**
     * 设置列对应的数据属性
     *
     * @param fields 列对应属性
     * @return ExcelSheetImport
     * @throws ExcelException 参考实现
     */
    @Override
    default ExcelSheetImport setColumnFields(String... fields) throws ExcelException {
        return setColumnFields(Arrays.asList(fields), HashMap.class);
    }

    /**
     * 设置列对应的数据属性
     *
     * @param rowSpan 占用行数
     * @param fields  列对应的属性
     * @return ExcelSheetImport
     * @throws ExcelException 参考实现
     */
    @Override
    default ExcelSheetImport setColumnFields(int rowSpan, String... fields) throws ExcelException {
        return setColumnFields(rowSpan, Arrays.asList(fields), HashMap.class);
    }

    /**
     * 读取行
     *
     * @param clazz 数据类型
     * @param <T>   容器类型
     * @return ExcelSheetImport
     * @throws ExcelException         参考实现
     * @throws InstantiationException 参考实现
     * @throws IllegalAccessException 参考实现
     */
    @Override
    <T> ExcelSheetImport readRows(Class<T> clazz) throws ExcelException, InstantiationException, IllegalAccessException;

    /**
     * 读取行
     *
     * @param clazz      数据类型
     * @param handlerRow 操作当前行数据
     * @param <T>        容器类型
     * @return ExcelSheetImport
     * @throws ExcelException         参考实现
     * @throws InstantiationException 参考实现
     * @throws IllegalAccessException 参考实现
     */
    @Override
    <T> ExcelSheetImport readRows(Class<T> clazz, HandlerRowA<T> handlerRow) throws ExcelException, InstantiationException, IllegalAccessException;

    /**
     * 读取行
     *
     * @param clazz      数据类型
     * @param handlerRow 操作当前行数据,返回false不继续读取下一行
     * @param <T>        容器类型
     * @return ExcelSheetImport
     * @throws ExcelException         参考实现
     * @throws InstantiationException 参考实现
     * @throws IllegalAccessException 参考实现
     */
    @Override
    <T> ExcelSheetImport readRows(Class<T> clazz, HandlerRowB<T> handlerRow) throws ExcelException, InstantiationException, IllegalAccessException;

    /**
     * 读取行
     *
     * @return ExcelSheetImport
     * @throws ExcelException         参考实现
     * @throws InstantiationException 参考实现
     * @throws IllegalAccessException 参考实现
     */
    @Override
    ExcelSheetImport readRows() throws ExcelException, IllegalAccessException, InstantiationException;

    /**
     * 读取行
     *
     * @param handlerRow 操作当前行数据
     * @param <T>        容器类型
     * @return ExcelSheetImport
     * @throws ExcelException         参考实现
     * @throws InstantiationException 参考实现
     * @throws IllegalAccessException 参考实现
     */
    @Override
    <T> ExcelSheetImport readRows(HandlerRowA<T> handlerRow) throws ExcelException, IllegalAccessException, InstantiationException;

    /**
     * 读取行
     *
     * @param handlerRow 操作当前行数据,返回false不继续读取下一行
     * @param <T>        容器类型
     * @return ExcelSheetImport
     * @throws ExcelException         参考实现
     * @throws InstantiationException 参考实现
     * @throws IllegalAccessException 参考实现
     */
    @Override
    <T> ExcelSheetImport readRows(HandlerRowB<T> handlerRow) throws ExcelException, IllegalAccessException, InstantiationException;
}
