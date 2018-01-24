package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.exception.ExcelTitleException;
import com.avalon.holygrail.excel.exception.ExportException;
import com.avalon.holygrail.excel.model.ExcelTitleCellAbstract;
import com.avalon.holygrail.excel.model.XSSFExcelTitle;
import com.avalon.holygrail.excel.model.XSSFMergeCell;
import com.avalon.holygrail.excel.norm.ExcelSheetImport;
import com.avalon.holygrail.excel.norm.ExcelWorkBookImport;
import com.avalon.holygrail.excel.norm.MergeCell;
import com.avalon.holygrail.excel.norm.SheetImportHandler;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * XSSFWorkBook SheetImportHandler
 * Created by 白超 on 2018/1/24.
 */
public class XSSFExcelSheetImport extends XSSFExcelWorkBookImport implements ExcelSheetImport {

    protected XSSFSheet sheet;//当前数据表对象

    protected XSSFExcelWorkBookImport ownerWorkBook;//所属工作簿对象

    protected List<MergeCell> titleMergeCells;//表头合并单元格信息

    protected LinkedList<MergeCell> dataTitleMergeCells;//与数据相关的表头信息

    protected int rowCursor = -1;//行游标,记录每次插入数据时的总起始行号

    protected int colCursor = -1;//列游标,记录每次插入数据时的总起始列号

    protected int physicalNumberOfRows;//物理行数

    protected Class<?> clazz = HashMap.class;//数据容器

    public XSSFExcelSheetImport(XSSFSheet sheet, XSSFExcelWorkBookImport ownerWorkBook) {
        this.sheet = sheet;
        this.ownerWorkBook = ownerWorkBook;
        this.physicalNumberOfRows = this.sheet.getPhysicalNumberOfRows();
    }

    @Override
    public XSSFMergeCell buildTitleMergeCell(ExcelTitleCellAbstract excelTitle, int startRow, int endRow, int startCol, int endCol) {
        return super.buildTitleMergeCell(excelTitle, startRow, endRow, startCol, endCol);
    }

    @Override
    public ExcelSheetImport setRowCursor(Function<Integer, Integer> handler) {
        this.rowCursor = handler.apply(this.rowCursor);
        return this;
    }

    @Override
    public ExcelSheetImport setColCursor(Function<Integer, Integer> handler) {
        this.colCursor = handler.apply(this.colCursor);
        return this;
    }

    @Override
    public <T> ExcelSheetImport parseTitlesJson(String titlesJson, Class<T> clazz) throws ExcelTitleException, ExportException {
        XSSFExcelTitle[][] excelTitles = this.parseCellsJson(titlesJson);
        return setTitles(excelTitles, clazz);
    }

    @Override
    public <T> ExcelSheetImport parseTitlesJson(InputStream inputStream, Class<T> clazz) throws IOException, ExcelTitleException, ExportException {
        XSSFExcelTitle[][] excelTitles = (XSSFExcelTitle[][]) this.parseCellsJson(inputStream);
        return setTitles(excelTitles, clazz);
    }

    @Override
    public <T> ExcelSheetImport parseTitlesJson(File file, Class<T> clazz) throws IOException, ExcelTitleException, ExportException {
        XSSFExcelTitle[][] excelTitles = (XSSFExcelTitle[][]) this.parseCellsJson(file);
        return setTitles(excelTitles, clazz);
    }

    @Override
    public <T> ExcelSheetImport setTitles(ExcelTitleCellAbstract[][] excelTitles, Class<T> clazz) throws ExcelTitleException, ExportException {
        if (!(excelTitles instanceof XSSFExcelTitle[][])) {
            throw new ExportException("SXSSFExcelSheetExport setTitles excelTitles类型应该为XSSFExcelTitle[][]");
        }
        this.titleMergeCells = handlerExcelTitles(excelTitles);
        this.dataTitleMergeCells = this.searchDataTitleMergeCell(this.titleMergeCells);
        this.clazz = clazz;
        return this;
    }

    @Override
    public <T> ExcelSheetImport setColumnFields(List<String> fields, Class<T> clazz) throws ExcelTitleException, ExportException {
        XSSFExcelTitle[][] excelTitles = new XSSFExcelTitle[1][fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            excelTitles[0][i] = new XSSFExcelTitle(fields.get(i));
        }
        return setTitles(excelTitles, clazz);
    }

    @Override
    public ExcelWorkBookImport getOwnerWorkBook() {
        return this.ownerWorkBook;
    }

    @Override
    public int getPhysicalNumberOfRows() {
        return this.physicalNumberOfRows;
    }

    @Override
    public SheetImportHandler readRows() {
        return null;
    }

}
