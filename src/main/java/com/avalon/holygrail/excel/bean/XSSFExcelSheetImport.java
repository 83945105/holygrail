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
import org.apache.poi.ss.util.CellRangeAddress;
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

    protected Class<?> clazz = ArrayList.class;//数据容器

    protected ArrayList<ArrayList<?>> loadDatasList = new ArrayList<>();//每次读取的数据集合,按照读取次数顺序放入

    public XSSFExcelSheetImport(XSSFSheet sheet, XSSFExcelWorkBookImport ownerWorkBook) {
        super(ownerWorkBook.xssfWorkbook);
        this.sheet = sheet;
        this.ownerWorkBook = ownerWorkBook;
        this.physicalNumberOfRows = this.sheet.getPhysicalNumberOfRows();
    }

    protected void loadMap(Row row, Map<String, Object> container) throws ExcelException, IllegalAccessException, InstantiationException {
        Iterator<Cell> cells = row.iterator();
        int j = 0;
        XSSFCell cell;
        while (cells.hasNext()) {
            if (j < colCursor) {//小于列游标不读
                continue;
            }
            cell = (XSSFCell) cells.next();

            XSSFLoader xssfLoader = new XSSFLoader(this.sheet, cell);
            XSSFMergeCell mergeCell = new XSSFMergeCell();
            xssfLoader.copyCellStyleByValue(mergeCell);

            XSSFMergeCell tMergeCell = (XSSFMergeCell) this.serchMergeCell(this.dataTitleMergeCells, cell.getColumnIndex());

            if(tMergeCell == null) {
                container.put("["+cell.getRowIndex()+","+cell.getColumnIndex()+"]", xssfLoader.getValue());
            }else {
                tMergeCell.copyCellOptionSelective(mergeCell);
                xssfLoader.copyCellOptionSelective(mergeCell);
                container.put(mergeCell.getField(), mergeCell.getValue());
            }
        }
    }

    protected void loadCollection(Row row, Collection<?> container) {

    }

    public static void main(String[] args) {
        Class c = Map.class;
        System.out.println(Collection.class.isAssignableFrom(c));
    }

    protected <T> T loadRow(Class<T> clazz, Row row) throws ExcelException, InstantiationException, IllegalAccessException {
        T rs = clazz.newInstance();
        if (Map.class.isAssignableFrom(clazz)) {//Map集合
            this.loadMap(row, (Map<String, Object>) rs);
            return rs;
        }
        if (Collection.class.isAssignableFrom(clazz)) {//表示使用集合去装载数据,此时不记录field
            this.loadCollection(row, (Collection<?>) rs);
            return rs;
        }
        return null;
    }

    protected <T> void loadRows(Class<T> clazz) throws ExcelException, IllegalAccessException, InstantiationException {
        Iterator<Row> rows = this.sheet.iterator();
        int i = 0;
        int start = rowCursor;//开始读取的行号
        ArrayList<T> records = new ArrayList<>();
        loadDatasList.add(records);//每次读取向总容器中创建一个子容器
        while (rows.hasNext()) {
            if (i++ <= start) {//小于等于行游标的不读
                rows.next();
                continue;
            }
            T row = this.loadRow(clazz, rows.next());
            records.add(row);
            rowCursor++;//读完一行游标下移
        }
        loadDatasList.add(records);
    }

    /**
     * 解析表头
     *
     * @param titles 表头合并单元格信息
     */
    protected void parseExportTitles(Collection<MergeCell> titles) throws ExcelException {
        int maxRowIndex = rowCursor;
        for (MergeCell title : titles) {
            XSSFMergeCell mergeCell = (XSSFMergeCell) title;
            if (mergeCell.getEndRow() > maxRowIndex) {
                maxRowIndex = mergeCell.getEndRow();
            }
        }
        //记录行号
        int finalMaxRowIndex = maxRowIndex;
        setRowCursor(idx -> finalMaxRowIndex);
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
        this.parseExportTitles(this.dataTitleMergeCells);
        //this.dataTitleFields = this.searchDataTitleFields(this.dataTitleMergeCells);
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
    public <T> SheetImportHandler readRows(Class<T> clazz) throws ExcelException, InstantiationException, IllegalAccessException {
        this.loadRows(clazz);
        return this;
    }

}
