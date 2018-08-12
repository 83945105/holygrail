package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.model.BaseExcelTitleCell;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * Sheet导入操作
 *
 * @author 白超
 * @date 2018/1/24
 */
public interface SheetImportHandler extends Sheet {

    /**
     * 获取所属Excel工作簿
     *
     * @return Excel工作簿
     */
    @Override
    ExcelWorkBookImport getOwnerWorkBook();

    /**
     * 操作行
     */
    @FunctionalInterface
    interface HandlerRowA<T> {

        /**
         * 接收行
         *
         * @param record  当前行数据对象
         * @param records 数据容器
         * @param rowNum  行号
         * @param index   当前行数据在数据容器中的下标
         */
        void accept(T record, ArrayList<T> records, int rowNum, int index);
    }

    /**
     * 操作行
     */
    @FunctionalInterface
    interface HandlerRowB<T> {

        /**
         * 接收行
         *
         * @param record  当前行数据对象
         * @param records 数据容器
         * @param rowNum  行号
         * @param index   当前行数据在数据容器中的下标
         * @return 是否继续读取下一行
         */
        boolean apply(T record, ArrayList<T> records, int rowNum, int index);
    }

    @Override
    SheetImportHandler setRowCursor(Function<Integer, Integer> handler);

    @Override
    SheetImportHandler setColCursor(Function<Integer, Integer> handler);

    @Override
    default SheetImportHandler setRowNum(Function<Integer, Integer> handler) {
        return this.setRowCursor(rowCursor -> handler.apply(rowCursor + 1) - 1);
    }

    @Override
    default SheetImportHandler setColumnNum(Function<Integer, Integer> handler) {
        return this.setColCursor(colCursor -> handler.apply(colCursor + 1) - 1);
    }

    /**
     * 解析表头json数据
     *
     * @param titlesJson 表头数据json
     * @param clazz      数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler parseTitlesJson(String titlesJson, Class<T> clazz) throws ExcelException;

    /**
     * 解析表头json文件
     *
     * @param inputStream 表头数据流
     * @param clazz       数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler parseTitlesJson(InputStream inputStream, Class<T> clazz) throws IOException, ExcelException;

    /**
     * 解析表头json文件
     *
     * @param file  表头数据文件
     * @param clazz 数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler parseTitlesJson(File file, Class<T> clazz) throws IOException, ExcelException;

    /**
     * 设置表头
     *
     * @param titles 表头对象
     * @param clazz  数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler setTitles(BaseExcelTitleCell[][] titles, Class<T> clazz) throws ExcelException;

    /**
     * 设置列对应的数据属性
     *
     * @param fields 属性
     * @param clazz  数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler setColumnFields(List<String> fields, Class<T> clazz) throws ExcelException;

    /**
     * 设置列对应的数据属性
     *
     * @param rowSpan 占用行数
     * @param fields  属性
     * @param clazz   数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler setColumnFields(int rowSpan, List<String> fields, Class<T> clazz) throws ExcelException;

    /**
     * 设置列值
     * 注意,使用该方法读取数据,设置的field应该与对应数据的列号相同
     *
     * @param fields
     * @return
     * @throws ExcelException
     */
    default SheetImportHandler setColumnFields(String... fields) throws ExcelException {
        return setColumnFields(Arrays.asList(fields), HashMap.class);
    }

    /**
     * 设置列值
     * 注意,使用该方法读取数据,设置的field应该与对应数据的列号相同
     *
     * @param rowSpan 占用行数
     * @param fields
     * @return
     * @throws ExcelException
     */
    default SheetImportHandler setColumnFields(int rowSpan, String... fields) throws ExcelException {
        return setColumnFields(rowSpan, Arrays.asList(fields), HashMap.class);
    }

    @Override
    default SheetImportHandler parseTitlesJson(InputStream inputStream) throws IOException, ExcelException {
        return parseTitlesJson(inputStream, HashMap.class);
    }

    @Override
    default SheetImportHandler parseTitlesJson(File file) throws IOException, ExcelException {
        return parseTitlesJson(file, HashMap.class);
    }

    @Override
    default SheetImportHandler parseTitlesJson(String titlesJson) throws ExcelException {
        return parseTitlesJson(titlesJson, HashMap.class);
    }

    @Override
    default SheetImportHandler setTitles(BaseExcelTitleCell[][] titles) throws ExcelException {
        return setTitles(titles, HashMap.class);
    }

    /**
     * 获取物理行数
     *
     * @return
     */
    int getPhysicalNumberOfRows();

    /**
     * 读取数据
     *
     * @param clazz 数据类型
     * @param <T>
     * @return 当前对象
     * @throws ExcelException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    <T> SheetImportHandler readRows(Class<T> clazz) throws ExcelException, InstantiationException, IllegalAccessException;

    /**
     * 读取数据
     *
     * @param clazz      数据类型
     * @param handlerRow 操作当前行数据
     * @param <T>
     * @return 当前对象
     * @throws ExcelException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    <T> SheetImportHandler readRows(Class<T> clazz, HandlerRowA<T> handlerRow) throws ExcelException, InstantiationException, IllegalAccessException;

    /**
     * 读取数据
     *
     * @param clazz      数据类型
     * @param handlerRow 操作当前行数据,返回false不继续读取下一行
     * @param <T>
     * @return 当前对象
     * @throws ExcelException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    <T> SheetImportHandler readRows(Class<T> clazz, HandlerRowB<T> handlerRow) throws ExcelException, InstantiationException, IllegalAccessException;

    /**
     * 读取数据(使用默认数据类型或者表头设置的数据类型)
     *
     * @return 当前对象
     * @throws ExcelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    SheetImportHandler readRows() throws ExcelException, IllegalAccessException, InstantiationException;

    /**
     * 读取数据(使用默认数据类型或者表头设置的数据类型)
     *
     * @param handlerRow 操作当前行数据
     * @return 当前对象
     * @throws ExcelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    <T> SheetImportHandler readRows(HandlerRowA<T> handlerRow) throws ExcelException, IllegalAccessException, InstantiationException;

    /**
     * 读取数据(使用默认数据类型或者表头设置的数据类型)
     *
     * @param handlerRow 操作当前行数据,返回false不继续读取下一行
     * @return 当前对象
     * @throws ExcelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    <T> SheetImportHandler readRows(HandlerRowB<T> handlerRow) throws ExcelException, IllegalAccessException, InstantiationException;

    /**
     * 获取读到的数据
     *
     * @param <T> 数据类型
     * @return
     */
    <T> ArrayList<T> getReadData();

    /**
     * 根据下标获取第N次读取的数据
     *
     * @param index 下标
     * @param <T>   数据类型
     * @return
     */
    <T> ArrayList<T> getReadData(int index);

    /**
     * 获取所有读取的数据
     *
     * @param <T> 数据类型
     * @return
     */
    <T> ArrayList<T> getAllReadData();
}
