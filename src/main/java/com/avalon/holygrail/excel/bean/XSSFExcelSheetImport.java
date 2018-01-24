package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.exception.ExportException;
import com.avalon.holygrail.excel.model.ExcelTitleCellAbstract;
import com.avalon.holygrail.excel.model.XSSFExcelTitle;
import com.avalon.holygrail.excel.model.XSSFMergeCell;
import com.avalon.holygrail.excel.norm.ExcelSheetImport;
import com.avalon.holygrail.excel.norm.ExcelWorkBookImport;
import com.avalon.holygrail.excel.norm.MergeCell;
import com.avalon.holygrail.excel.norm.SheetImportHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
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

    protected ArrayList<String> dataTitleFields;//与数据相关的field

    protected int rowCursor = -1;//行游标,记录读取起始行号

    protected int colCursor = -1;//列游标,记录读取起始列号

    protected int physicalNumberOfRows;//物理行数

    protected Class<?> clazz = HashMap.class;//数据容器

    public XSSFExcelSheetImport(XSSFSheet sheet, XSSFExcelWorkBookImport ownerWorkBook) {
        this.sheet = sheet;
        this.ownerWorkBook = ownerWorkBook;
        this.physicalNumberOfRows = this.sheet.getPhysicalNumberOfRows();
    }

    protected void loadHashMap(Row row) throws ExcelException {
        Iterator<Cell> cells = row.iterator();
        int j = 0;
        XSSFCell cell;
        while (cells.hasNext()) {
            if (j < colCursor) {//小于列游标不读
                continue;
            }
            cell = (XSSFCell) cells.next();
            XSSFLoader xssfLoader = new XSSFLoader(cell);
            XSSFMergeCell mergeCell = new XSSFMergeCell();
            xssfLoader.copyCellOption(mergeCell);

        }
    }

    protected <T> void loadRow(Class<T> clazz, Row row) throws ExcelException {
        if (clazz == HashMap.class || clazz == Map.class) {
            this.loadHashMap(row);
            return;
        }
        if (clazz == LinkedHashMap.class) {

            return;
        }
    }

    protected <T> void loadRows(Class<T> clazz) throws ExcelException {
        Iterator<Row> rows = this.sheet.iterator();
        int i = 0;
        while (rows.hasNext()) {
            if (i < rowCursor) {//小于行游标的不读
                continue;
            }
            this.loadRow(clazz, rows.next());
        }
    }

    @Override
    public XSSFMergeCell buildTitleMergeCell(ExcelTitleCellAbstract excelTitle, int startRow, int endRow, int startCol, int endCol) throws ExcelException {
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
    public <T> ExcelSheetImport parseTitlesJson(String titlesJson, Class<T> clazz) throws ExcelException {
        XSSFExcelTitle[][] excelTitles = this.parseCellsJson(titlesJson);
        return setTitles(excelTitles, clazz);
    }

    @Override
    public <T> ExcelSheetImport parseTitlesJson(InputStream inputStream, Class<T> clazz) throws IOException, ExcelException {
        XSSFExcelTitle[][] excelTitles = (XSSFExcelTitle[][]) this.parseCellsJson(inputStream);
        return setTitles(excelTitles, clazz);
    }

    @Override
    public <T> ExcelSheetImport parseTitlesJson(File file, Class<T> clazz) throws IOException, ExcelException {
        XSSFExcelTitle[][] excelTitles = (XSSFExcelTitle[][]) this.parseCellsJson(file);
        return setTitles(excelTitles, clazz);
    }

    @Override
    public <T> ExcelSheetImport setTitles(ExcelTitleCellAbstract[][] excelTitles, Class<T> clazz) throws ExcelException {
        if (!(excelTitles instanceof XSSFExcelTitle[][])) {
            throw new ExportException("SXSSFExcelSheetExport setTitles excelTitles类型应该为XSSFExcelTitle[][]");
        }
        this.titleMergeCells = handlerExcelTitles(excelTitles);
        this.dataTitleMergeCells = this.searchDataTitleMergeCells(this.titleMergeCells);
        this.dataTitleFields = this.searchDataTitleFields(this.dataTitleMergeCells);
        this.clazz = clazz;
        return this;
    }

    @Override
    public <T> ExcelSheetImport setColumnFields(List<String> fields, Class<T> clazz) throws ExcelException {
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
    public <T> SheetImportHandler readRows(Class<T> clazz) throws ExcelException {
        this.loadRows(clazz);
        return this;
    }

}
