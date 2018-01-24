package com.avalon.holygrail.excel.norm;

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
 * Sheet导入操作
 * Created by 白超 on 2018/1/24.
 */
public interface SheetImportHandler extends Sheet {

    @Override
    SheetImportHandler setRowCursor(Function<Integer, Integer> handler);

    @Override
    SheetImportHandler setColCursor(Function<Integer, Integer> handler);

    /**
     * 解析表头json数据
     * @param titlesJson 表头数据json
     * @param clazz 数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler parseTitlesJson(String titlesJson, Class<T> clazz) throws ExcelTitleException, ExportException;

    /**
     * 解析表头json文件
     * @param inputStream 表头数据流
     * @param clazz 数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler parseTitlesJson(InputStream inputStream, Class<T> clazz) throws IOException, ExcelTitleException, ExportException;

    /**
     * 解析表头json文件
     * @param file 表头数据文件
     * @param clazz 数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler parseTitlesJson(File file, Class<T> clazz) throws IOException, ExcelTitleException, ExportException;

    /**
     * 设置表头
     * @param titles 表头对象
     * @param clazz 数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler setTitles(ExcelTitleCellAbstract[][] titles, Class<T> clazz) throws ExcelTitleException, ExportException;

    /**
     * 设置列对应的数据属性
     * @param fields 属性
     * @param clazz 数据容器
     * @return 准备导入
     */
    <T> SheetImportHandler setColumnFields(List<String> fields, Class<T> clazz) throws ExcelTitleException, ExportException;

    default SheetImportHandler setColumnFields(String... fields) throws ExcelTitleException, ExportException {
        return setColumnFields(Arrays.asList(fields), Map.class);
    }

    @Override
    default SheetImportHandler parseTitlesJson(InputStream inputStream) throws IOException, ExcelTitleException, ExportException {
        return parseTitlesJson(inputStream, Map.class);
    }

    @Override
    default SheetImportHandler parseTitlesJson(File file) throws IOException, ExcelTitleException, ExportException {
        return parseTitlesJson(file, Map.class);
    }

    @Override
    default SheetImportHandler parseTitlesJson(String titlesJson) throws ExcelTitleException, ExportException {
        return parseTitlesJson(titlesJson, Map.class);
    }

    @Override
    default SheetImportHandler setTitles(ExcelTitleCellAbstract[][] titles) throws ExcelTitleException, ExportException {
        return setTitles(titles, Map.class);
    }

    /**
     * 获取物理行数
     * @return
     */
    int getPhysicalNumberOfRows();

    SheetImportHandler readRows();
}
