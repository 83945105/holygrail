package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.exception.ExcelTitleException;
import com.avalon.holygrail.excel.exception.ExportException;
import com.avalon.holygrail.excel.model.ExcelTitleCellAbstract;
import com.avalon.holygrail.excel.model.SXSSFExcelTitle;
import com.avalon.holygrail.excel.model.SXSSFMergeCell;
import com.avalon.holygrail.excel.norm.ExcelSheetExport;
import com.avalon.holygrail.excel.norm.MergeCell;
import com.avalon.holygrail.util.ClassUtil;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

/**
 * SXSSFWorkbook SheetExport
 * Created by 白超 on 2018/1/17.
 */
public class SXSSFExcelExportSheet extends SXSSFExcelExportWorkBook implements ExcelSheetExport {

    protected SXSSFSheet sheet;//当前数据表对象

    protected SXSSFExcelExportWorkBook ownerWorkBook;//所属工作簿对象

    protected List<MergeCell> titleMergeCells;//表头合并单元格信息

    protected LinkedList<MergeCell> dataTitleMergeCells;//与数据相关的表头信息

    protected int rowCursor = -1;//行游标,记录每次插入数据时的总起始行号

    protected int colCursor = -1;//列游标,记录每次插入数据时的总起始列号

    protected int totalDataSize;//数据记录总数

    public SXSSFExcelExportSheet(String sheetName, SXSSFExcelExportWorkBook ownerWorkBook) {
        this.sheet = (SXSSFSheet) this.sxssfWorkbook.createSheet(sheetName);
        this.ownerWorkBook = ownerWorkBook;
    }

    public SXSSFExcelExportSheet(SXSSFWorkbook workbook, String sheetName, SXSSFExcelExportWorkBook ownerWorkBook) {
        super(workbook);
        this.sheet = (SXSSFSheet) this.sxssfWorkbook.createSheet(sheetName);
        this.ownerWorkBook = ownerWorkBook;
    }

    @FunctionalInterface
    private interface Consumer<T> {
        void accept(T t) throws ExportException;
    }

    /**
     * 处理行
     * 存在获取,不存在创建
     *
     * @param rowIndex
     * @param handler
     */
    protected void handlerRow(int rowIndex, Consumer<SXSSFRow> handler) throws ExportException {
        SXSSFRow row = (SXSSFRow) this.sheet.getRow(rowIndex);
        if (row == null) {
            if (rowCursor >= rowIndex) {
                throw new RuntimeException("SXSSFExcelExportSheet parseExportTitles rowCursor位置异常");
            }
            row = (SXSSFRow) this.sheet.createRow(rowIndex);
        }
        handler.accept(row);
    }

    /**
     * 处理行的单元格
     * 存在获取,不存在创建
     *
     * @param row
     * @param cellIndex
     * @param handler
     */
    protected void handlerCell(SXSSFRow row, int cellIndex, Consumer<SXSSFCell> handler) throws ExportException {
        SXSSFCell cell = (SXSSFCell) row.getCell(cellIndex);
        if (cell == null) {
            cell = (SXSSFCell) row.createCell(cellIndex, SXSSFCell.CELL_TYPE_STRING);
        }
        handler.accept(cell);
    }

    @Override
    public SXSSFMergeCell buildTitleMergeCell(ExcelTitleCellAbstract excelTitle, int startRow, int endRow, int startCol, int endCol) {
        SXSSFMergeCell mergeCell = new SXSSFMergeCell();

        mergeCell.setCellRangeAddress(new CellRangeAddress(rowCursor + startRow + 1, rowCursor + endRow + 1, colCursor + startCol + 1, colCursor + endCol + 1));

        excelTitle.copyCellOption(mergeCell);//设置属性
        excelTitle.copyCellStyle(mergeCell);//设置样式

        return mergeCell;
    }

    /**
     * 构建单元格
     * @param mergeCell 单元格相关信息
     * @param cell 单元格对象
     */
    protected void buildCell(SXSSFMergeCell mergeCell, SXSSFCell cell) {
        mergeCell.setDataValidationHelper(this.sheet.getDataValidationHelper());
        DataValidation dataValidation = mergeCell.getDataValidation();
        if(dataValidation != null) {
            this.sheet.addValidationData(mergeCell.getDataValidation());
        }
        XSSFCellStyle cellStyle = (XSSFCellStyle) this.sxssfWorkbook.createCellStyle();
        SXSSFLoader sxssfLoader = new SXSSFLoader(this.sheet, cell, cellStyle);
        //设置属性
        mergeCell.copyCellOption(sxssfLoader);
        //设置样式
        mergeCell.copyCellStyle(sxssfLoader);
        //装入表格样式
        cell.setCellStyle(cellStyle);
    }

    /**
     * 解析表头
     *
     * @param titles 表头合并单元格信息
     */
    protected void parseExportTitles(Collection<MergeCell> titles) throws ExportException {
        int maxRowIndex = rowCursor;
        for (MergeCell title : titles) {
            SXSSFMergeCell mergeCell = (SXSSFMergeCell) title;
            if (mergeCell.getEndRow() > maxRowIndex) {
                maxRowIndex = mergeCell.getEndRow();
            }
            //开始处理合并单元格
            for (int i = 0; i < mergeCell.getRowSpan(); i++) {//循环占用行数
                //先尝试获取当前占用行,取起始行+当前占用行数为行游标
                int finalI = i;
                handlerRow(mergeCell.getStartRow() + i, row -> {
                    for (int j = 0; j < mergeCell.getColSpan(); j++) {//循环占用列数
                        //先尝试获取当前占用单元格,取起始列+当前占用列数为列游标
                        int cc = mergeCell.getStartCol() + j;
                        int finalJ = j;
                        handlerCell(row, cc, cell -> {
                            if (finalI == 0 && finalJ == 0) {//当为合并单元格的左上角第一个单元格时,设置相关属性
                                this.buildCell(mergeCell, cell);
                            }
                        });
                    }
                });
            }
            //添加合并单元格
            this.sheet.addMergedRegion(mergeCell.getCellRangeAddress());
        }
        //记录行号
        int finalMaxRowIndex = maxRowIndex;
        setRowCursor(idx -> finalMaxRowIndex);
    }

    /**
     * 设置合并单元格宽度
     *
     * @param mergeCell
     */
    protected void setMergeCellColumnWidth(SXSSFMergeCell mergeCell) {
        int width = mergeCell.getWidth() * 256 / mergeCell.getColSpan();
        for (int i = mergeCell.getStartCol(); i <= mergeCell.getEndCol(); i++) {
            this.setColumnWidth(i, width);
        }
    }

    protected <T> void parseRecord(T record) throws ExportException {
        if(record instanceof Map) {
            this.parseMap((Map<String, Object>) record);
            return;
        }
        this.parseObject(record);
    }

    protected <T> void parseRecord(T record, int index, FormatterCell<T> formatter) throws ExportException {
        if(record instanceof Map) {
            this.parseMap((Map<String, Object>) record, index, (FormatterCell<Map<String, Object>>) formatter);
            return;
        }
        this.parseObject(record, index, formatter);
    }

    protected void parseMap(Map<String, Object> record) throws ExportException {
        int rowIndex = rowCursor + 1;
        handlerRow(rowIndex, row -> {
            int colCursor = 0;//列游标
            for (MergeCell titleMergeCell : dataTitleMergeCells) {
                SXSSFMergeCell tMergeCell = (SXSSFMergeCell) titleMergeCell;
                SXSSFMergeCell mergeCell = new SXSSFMergeCell();
                tMergeCell.copyCellOption(mergeCell);
//                    tMergeCell.copyCellStyle(mergeCell);
                //创建当前行的合并单元格
                mergeCell.setCellRangeAddress(new CellRangeAddress(rowIndex, rowIndex, tMergeCell.getStartCol(), tMergeCell.getEndCol()));

                for (Map.Entry<String, Object> entry : record.entrySet()) {
                    if(!entry.getKey().equals(mergeCell.getField())) {
                        continue;
                    }
                    handlerCell(row, colCursor, cell -> {
                        try {
                            Object value = entry.getValue();
                            mergeCell.setValue(value == null ? "" : value.toString());
                        } catch (Exception e) {
                            mergeCell.setValue("");
                        }
                        this.buildCell(mergeCell, cell);
                    });
                    break;
                }
                colCursor += mergeCell.getColSpan();//移动游标
                //添加合并单元格
                this.sheet.addMergedRegion(mergeCell.getCellRangeAddress());
            }
            //记录行游标
            this.setRowCursor(idx -> rowIndex);
        });
    }

    protected void parseMap(Map<String, Object> record, int index, FormatterCell<Map<String, Object>> formatter) throws ExportException {
        int rowIndex = rowCursor + 1;
        handlerRow(rowIndex, row -> {
            int colCursor = 0;//列游标
            for (MergeCell titleMergeCell : dataTitleMergeCells) {
                SXSSFMergeCell tMergeCell = (SXSSFMergeCell) titleMergeCell;
                SXSSFMergeCell mergeCell = new SXSSFMergeCell();
                tMergeCell.copyCellOption(mergeCell);
//                    tMergeCell.copyCellStyle(mergeCell);
                //创建当前行的合并单元格
                mergeCell.setCellRangeAddress(new CellRangeAddress(rowIndex, rowIndex, tMergeCell.getStartCol(), tMergeCell.getEndCol()));
                int i = 0;
                for (Map.Entry<String, Object> entry : record.entrySet()) {
                    boolean last = i++ == record.size() - 1;
                    boolean equal = entry.getKey().equals(mergeCell.getField());
                    if (!equal && !last) {//不等于且不是最后一条,继续
                        continue;
                    }
                    handlerCell(row, colCursor, cell -> {
                        Object value;
                        if(equal) {//等于
                            try {
                                value = entry.getValue();
                            } catch (Exception e) {
                                value = "";
                            }
                        }else {//最后一条依然不等于,给个默认值""
                            value = "";
                        }
                        value = formatter.apply(value, record, mergeCell, mergeCell.getField(), rowCursor, index);
                        mergeCell.setValue(value);
                        this.buildCell(mergeCell, cell);
                    });
                    break;
                }
                colCursor += mergeCell.getColSpan();//移动游标
                //添加合并单元格
                this.sheet.addMergedRegion(mergeCell.getCellRangeAddress());
            }
            //记录行游标
            this.setRowCursor(idx -> rowIndex);
        });
    }

    protected <T> void parseObject(T record) throws ExportException {
        ArrayList<Field> fs = ClassUtil.getAllFields(record.getClass());
        int rowIndex = rowCursor + 1;
        handlerRow(rowIndex, row -> {
            int colCursor = 0;//列游标
            for (MergeCell titleMergeCell : dataTitleMergeCells) {
                SXSSFMergeCell tMergeCell = (SXSSFMergeCell) titleMergeCell;
                SXSSFMergeCell mergeCell = new SXSSFMergeCell();
                tMergeCell.copyCellOption(mergeCell);
//                    tMergeCell.copyCellStyle(mergeCell);
                //创建当前行的合并单元格
                mergeCell.setCellRangeAddress(new CellRangeAddress(rowIndex, rowIndex, tMergeCell.getStartCol(), tMergeCell.getEndCol()));
                for (Field f : fs) {
                    f.setAccessible(true);
                    if (!f.getName().equals(mergeCell.getField())) {
                        continue;
                    }
                    handlerCell(row, colCursor, cell -> {
                        try {
                            Object value = new PropertyDescriptor(f.getName(), record.getClass()).getReadMethod().invoke(record);
                            mergeCell.setValue(value == null ? "" : value.toString());
                        } catch (Exception e) {
                            mergeCell.setValue("");
                        }
                        this.buildCell(mergeCell, cell);
                    });
                    break;
                }
                colCursor += mergeCell.getColSpan();//移动游标
                //添加合并单元格
                this.sheet.addMergedRegion(mergeCell.getCellRangeAddress());
            }
            //记录行游标
            this.setRowCursor(idx -> rowIndex);
        });
    }

    protected <T> void parseObject(T record, int index, FormatterCell<T> formatter) throws ExportException {
        ArrayList<Field> fs = ClassUtil.getAllFields(record.getClass());
        int rowIndex = rowCursor + 1;
        handlerRow(rowIndex, row -> {
            int colCursor = 0;//列游标
            for (MergeCell titleMergeCell : dataTitleMergeCells) {
                SXSSFMergeCell tMergeCell = (SXSSFMergeCell) titleMergeCell;
                SXSSFMergeCell mergeCell = new SXSSFMergeCell();
                tMergeCell.copyCellOption(mergeCell);
//                    tMergeCell.copyCellStyle(mergeCell);
                //创建当前行的合并单元格
                mergeCell.setCellRangeAddress(new CellRangeAddress(rowIndex, rowIndex, tMergeCell.getStartCol(), tMergeCell.getEndCol()));
                for (int i = 0; i < fs.size(); i++) {
                    Field f = fs.get(i);
                    f.setAccessible(true);
                    boolean last = i == fs.size() - 1;
                    boolean equal = f.getName().equals(mergeCell.getField());
                    if (!equal && !last) {
                        continue;
                    }
                    handlerCell(row, colCursor, cell -> {
                        Object value;
                        if(equal) {//等于
                            try {
                                value = new PropertyDescriptor(f.getName(), record.getClass()).getReadMethod().invoke(record);
                            } catch (Exception e) {
                                value = "";
                            }
                        }else {//最后一条依然不等于,给个默认值""
                            value = "";
                        }
                        value = formatter.apply(value, record, mergeCell, mergeCell.getField(), rowCursor, index);
                        mergeCell.setValue(value);
                        this.buildCell(mergeCell, cell);
                    });
                    break;
                }
                colCursor += mergeCell.getColSpan();//移动游标
                //添加合并单元格
                this.sheet.addMergedRegion(mergeCell.getCellRangeAddress());
            }
            //记录行游标
            this.setRowCursor(idx -> rowIndex);
        });
    }

    /**
     * 解析数据
     *
     * @param records 数据集合
     * @param <T>     数据类型
     */
    protected <T> void parseExportData(Collection<T> records) throws ExportException {
        this.totalDataSize += records.size();
        //找到了表头对应的数据
        for (T record : records) {
            this.parseRecord(record);
        }
    }

    @Override
    public void export(String outPath) throws IOException {
        super.export(outPath);
    }

    @FunctionalInterface
    public interface FormatterCell<T> {

        /**
         * 格式化单元格
         *
         * @param value     当前单元格值
         * @param record    当前行数据
         * @param mergeCell 单元格信息
         * @param field     单元格所在列值
         * @param rowCursor 当前行游标
         * @param index     当前数据在数据集合中的下标
         * @return 你要设置的单元格值
         */
        Object apply(Object value, T record, SXSSFMergeCell mergeCell, String field, int rowCursor, int index) throws ExportException;
    }

    /**
     * 解析数据
     *
     * @param records   数据集合
     * @param formatter 格式化函数,接收5个参数,分别为 当前当前数据对象record、单元格信息、当前列值、游标、当前记录下标,需要返回要设置的单元格的值
     * @param <T>       数据类型
     */
    protected <T> void parseExportData(Collection<T> records, FormatterCell<T> formatter) throws ExportException {
        this.totalDataSize += records.size();
        int index = 0;
        for (T record : records) {
            this.parseRecord(record, index, formatter);
            index++;
        }
    }

    @Override
    public ExcelSheetExport setRowCursor(Function<Integer, Integer> handler) {
        rowCursor = handler.apply(rowCursor);
        return this;
    }

    @Override
    public ExcelSheetExport setColCursor(Function<Integer, Integer> handler) {
        colCursor = handler.apply(colCursor);
        return this;
    }

    @Override
    public SXSSFExcelExportWorkBook getOwnerWorkBook() {
        return this.ownerWorkBook;
    }

    @Override
    public ExcelSheetExport parseTitlesJson(String titlesJson, boolean exportTitles) throws ExcelTitleException, ExportException {
        SXSSFExcelTitle[][] excelTitles = this.parseCellsJson(titlesJson);
        return setTitles(excelTitles, exportTitles);
    }

    @Override
    public ExcelSheetExport parseTitlesJson(InputStream inputStream, boolean exportTitles) throws IOException, ExcelTitleException, ExportException {
        SXSSFExcelTitle[][] excelTitles = (SXSSFExcelTitle[][]) this.parseCellsJson(inputStream);
        return setTitles(excelTitles, exportTitles);
    }

    @Override
    public ExcelSheetExport parseTitlesJson(File file, boolean exportTitles) throws IOException, ExcelTitleException, ExportException {
        SXSSFExcelTitle[][] excelTitles = (SXSSFExcelTitle[][]) this.parseCellsJson(file);
        return setTitles(excelTitles, exportTitles);
    }

    @Override
    public ExcelSheetExport setTitles(ExcelTitleCellAbstract[][] excelTitles, boolean exportTitles) throws ExcelTitleException, ExportException {
        if (!(excelTitles instanceof SXSSFExcelTitle[][])) {
            throw new ExportException("SXSSFExcelExportSheet setTitles excelTitles类型应该为SXSSFExcelTitle[][]");
        }
        this.titleMergeCells = handlerExcelTitles(excelTitles);
        this.dataTitleMergeCells = this.searchDataTitleMergeCell(this.titleMergeCells);
        //设置列宽
        for (MergeCell mergeCell : dataTitleMergeCells) {
            this.setMergeCellColumnWidth((SXSSFMergeCell) mergeCell);
        }
        if (exportTitles) {
            parseExportTitles(this.titleMergeCells);
        }
        return this;
    }

    @Override
    public ExcelSheetExport setColumnFields(List<String> fields) throws ExcelTitleException, ExportException {
        SXSSFExcelTitle[][] excelTitles = new SXSSFExcelTitle[1][fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            excelTitles[0][i] = new SXSSFExcelTitle(fields.get(i));
        }
        return setTitles(excelTitles, false);
    }

    @Override
    public void setColumnWidth(int columnIndex, int width) {
        this.sheet.setColumnWidth(columnIndex, width);
    }

    @Override
    public <T> ExcelSheetExport importData(Collection<T> records) throws ExportException {
        this.parseExportData(records);
        return this;
    }

    @Override
    public <T> ExcelSheetExport importData(Collection<T> records, FormatterCell<T> formatter) throws ExportException {
        this.parseExportData(records, formatter);
        return this;
    }

    @Override
    public int getTotalDataSize() {
        return this.totalDataSize;
    }

}