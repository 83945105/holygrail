package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.model.BaseExcelTitleCell;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Sheet导出操作
 * Created by 白超 on 2018/1/19.
 */
public interface SheetExportHandler extends Sheet {

    @Override
    ExcelWorkBookExport getOwnerWorkBook();

    /**
     * 设置行游标
     * @param handler 接收行号,返回你想设置的行号
     */
    SheetExportHandler setRowCursor(Function<Integer, Integer> handler);

    /**
     * 设置列游标
     * @param handler 接收列号,返回你想设置的列号
     */
    SheetExportHandler setColCursor(Function<Integer, Integer> handler);

    /**
     * 解析表头json数据
     * @param titlesJson 表头数据json
     * @param exportTitles 是否导出表头
     * @return 准备导出
     */
    ExcelSheetExport parseTitlesJson(String titlesJson, boolean exportTitles) throws ExcelException;

    /**
     * 解析表头json文件
     * @param inputStream 表头数据流
     * @param exportTitles 是否导出表头
     * @return 准备导出
     */
    ExcelSheetExport parseTitlesJson(InputStream inputStream, boolean exportTitles) throws IOException, ExcelException;

    /**
     * 解析表头json文件
     * @param file 表头数据文件
     * @param exportTitles 是否导出表头
     * @return 准备导出
     */
    ExcelSheetExport parseTitlesJson(File file, boolean exportTitles) throws IOException, ExcelException;

    /**
     * 设置表头
     * @param titles 表头对象
     * @param exportTitles 是否导出表头
     * @return 准备导出
     */
    ExcelSheetExport setTitles(BaseExcelTitleCell[][] titles, boolean exportTitles) throws ExcelException;

    /**
     * 设置列属性
     * @param fields 属性
     * @return 准备导出
     */
    ExcelSheetExport setColumnFields(List<String> fields) throws ExcelException;

    /**
     * 设置列宽
     * @param columnIndex 列号
     * @param width 列宽
     */
    void setColumnWidth(int columnIndex, int width);

    /**
     * 插入图片
     * @param inputStream 图片流
     * @param pictureType 图片类型
     * @param dx1 起始单元格的x偏移量
     * @param dy1 起始单元格的y偏移量
     * @param dx2 终止单元格的x偏移量
     * @param dy2 终止单元格的y偏移量
     * @param col1 起始单元格列序号,从0开始计算
     * @param row1 起始单元格行序号,从0开始计算
     * @param col2 终止单元格列序号,从0开始计算
     * @param row2 终止单元格行序号,从0开始计算
     * @throws IOException
     */
    ExcelSheetExport insertPicture(InputStream inputStream, ExcelWorkBook.PictureType pictureType, int dx1, int dy1, int dx2, int dy2, int col1, int row1, int col2, int row2) throws IOException;

    /**
     * 设置列对应的数据属性
     * @param fields 属性
     * @return 准备导出
     */
    default ExcelSheetExport setColumnFields(String... fields) throws ExcelException {
        return setColumnFields(Arrays.asList(fields));
    }

    /**
     * 解析表头json文件
     * @param inputStream 表头数据流
     * @return 准备导出
     */
    default ExcelSheetExport parseTitlesJson(InputStream inputStream) throws IOException, ExcelException {
        return parseTitlesJson(inputStream, true);
    }

    /**
     * 解析表头json文件
     * @param file 表头数据文件
     * @return 准备导出
     */
    default ExcelSheetExport parseTitlesJson(File file) throws IOException, ExcelException {
        return parseTitlesJson(file, true);
    }

    /**
     * 解析表头json数据
     * @param titlesJson 表头数据json
     * @return 准备导出
     */
    default ExcelSheetExport parseTitlesJson(String titlesJson) throws ExcelException {
        return parseTitlesJson(titlesJson, true);
    }

    /**
     * 设置表头
     * @param titles 表头对象
     * @return 准备导出
     */
    default ExcelSheetExport setTitles(BaseExcelTitleCell[][] titles) throws ExcelException {
        return setTitles(titles, true);
    }

    /**
     * 插入图片
     * @param file 图片文件
     * @param pictureType 图片类型
     * @param dx1 起始单元格的x偏移量
     * @param dy1 起始单元格的y偏移量
     * @param dx2 终止单元格的x偏移量
     * @param dy2 终止单元格的y偏移量
     * @param col1 起始单元格列序号,从0开始计算
     * @param row1 起始单元格行序号,从0开始计算
     * @param col2 终止单元格列序号,从0开始计算
     * @param row2 终止单元格行序号,从0开始计算
     * @throws IOException
     */
    default ExcelSheetExport insertPicture(File file, ExcelWorkBook.PictureType pictureType, int dx1, int dy1, int dx2, int dy2, int col1, int row1, int col2, int row2) throws IOException {
        return insertPicture(new FileInputStream(file), pictureType, dx1, dy1, dx2, dy2, col1, row1, col2, row2);
    }

    /**
     * 插入图片
     * @param filePath 图片路径
     * @param pictureType 图片类型
     * @param dx1 起始单元格的x偏移量
     * @param dy1 起始单元格的y偏移量
     * @param dx2 终止单元格的x偏移量
     * @param dy2 终止单元格的y偏移量
     * @param col1 起始单元格列序号,从0开始计算
     * @param row1 起始单元格行序号,从0开始计算
     * @param col2 终止单元格列序号,从0开始计算
     * @param row2 终止单元格行序号,从0开始计算
     * @throws IOException
     */
    default ExcelSheetExport insertPicture(String filePath, ExcelWorkBook.PictureType pictureType, int dx1, int dy1, int dx2, int dy2, int col1, int row1, int col2, int row2) throws IOException {
        return insertPicture(new File(filePath), pictureType, dx1, dy1, dx2, dy2, col1, row1, col2, row2);
    }


    /**
     * 获取表格导入的数据总数
     * @return 数据总数
     */
    int getTotalDataSize();

    @Override
    default SheetExportHandler setRowNum(Function<Integer, Integer> handler) {
        return this.setRowCursor(rowCursor -> handler.apply(rowCursor + 1) - 1);
    }

    @Override
    default SheetExportHandler setColumnNum(Function<Integer, Integer> handler) {
        return this.setColCursor(colCursor -> handler.apply(colCursor + 1) - 1);
    }
}
