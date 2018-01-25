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
import com.avalon.holygrail.util.ClassUtil;
import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
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

    protected LinkedList<MergeCell> dataTitleMergeCells = new LinkedList<>();//与数据相关的表头信息

    protected ArrayList<String> dataTitleFields;//与数据相关的field

    protected int rowCursor = -1;//行游标,记录读取起始行号

    protected int colCursor = -1;//列游标,记录读取起始列号

    protected int physicalNumberOfRows;//物理行数

    protected Class<?> clazz = ArrayList.class;//数据容器

    private MethodAccess access = null;//对象的ASM,用于高效调用反射

    protected ArrayList<ArrayList<?>> loadDatasList = new ArrayList<>();//每次读取的数据集合,按照读取次数顺序放入

    public XSSFExcelSheetImport(XSSFSheet sheet, XSSFExcelWorkBookImport ownerWorkBook) {
        super(ownerWorkBook.xssfWorkbook);
        this.sheet = sheet;
        this.ownerWorkBook = ownerWorkBook;
        this.physicalNumberOfRows = this.sheet.getPhysicalNumberOfRows();
    }

    @FunctionalInterface
    private interface ParseCell {

        void handlerCell(XSSFCell cell) throws ExcelException;
    }

    /**
     * 解析行
     *
     * @param row
     * @param parseCell
     */
    protected void parseRow(Row row, ParseCell parseCell) throws ExcelException {
        Iterator<Cell> cells = row.iterator();
        int j = 0;
        XSSFCell cell;
        while (cells.hasNext()) {
            if (j < colCursor) {//小于列游标不读
                continue;
            }
            cell = (XSSFCell) cells.next();
            parseCell.handlerCell(cell);
        }
    }

    /**
     * 装载Map
     *
     * @param row
     * @param container
     * @throws ExcelException
     */
    protected void loadMap(Row row, Map<String, Object> container) throws ExcelException {
        this.parseRow(row, cell -> {
            XSSFLoader xssfLoader = new XSSFLoader(this.sheet, cell);
            XSSFMergeCell mergeCell = new XSSFMergeCell();
            xssfLoader.copyCellStyleByValue(mergeCell);
            XSSFMergeCell tMergeCell = (XSSFMergeCell) this.searchMergeCell(this.dataTitleMergeCells, cell.getColumnIndex());
            if (tMergeCell == null) {
                container.put("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "]", xssfLoader.getValue());
            } else {
                tMergeCell.copyCellOptionSelective(mergeCell);
                xssfLoader.copyCellOptionSelective(mergeCell);
                container.put(mergeCell.getField(), mergeCell.getValue());
            }
        });
    }

    /**
     * 装载集合
     *
     * @param row
     * @param container
     * @throws ExcelException
     */
    protected void loadCollection(Row row, Collection<Object> container) throws ExcelException {
        this.parseRow(row, cell -> {
            XSSFLoader xssfLoader = new XSSFLoader(this.sheet, cell);
            XSSFMergeCell mergeCell = new XSSFMergeCell();
            xssfLoader.copyCellStyleByValue(mergeCell);
            XSSFMergeCell tMergeCell = (XSSFMergeCell) this.searchMergeCell(this.dataTitleMergeCells, cell.getColumnIndex());
            if (tMergeCell == null) {
                container.add(xssfLoader.getValue());
            } else {
                tMergeCell.copyCellOptionSelective(mergeCell);
                xssfLoader.copyCellOptionSelective(mergeCell);
                container.add(mergeCell.getValue());
            }
        });
    }

    /**
     * 装载对象
     *
     * @param row
     * @param target
     * @param <T>
     * @throws ExcelException
     */
    protected <T> void loadObject(Row row, T target) throws ExcelException {
        this.parseRow(row, cell -> {
            XSSFLoader xssfLoader = new XSSFLoader(this.sheet, cell);
            XSSFMergeCell mergeCell = new XSSFMergeCell();
            xssfLoader.copyCellStyleByValue(mergeCell);
            XSSFMergeCell tMergeCell = (XSSFMergeCell) this.searchMergeCell(this.dataTitleMergeCells, cell.getColumnIndex());
            if (tMergeCell == null) {

            } else {
                tMergeCell.copyCellOptionSelective(mergeCell);
                xssfLoader.copyCellOptionSelective(mergeCell);
                this.typeLoader(mergeCell.getValue(), ClassUtil.getSetterMethodName(mergeCell.getField()), target);
            }
        });
    }

    protected <T> void typeLoader(Object value, String methodName, T target) throws ExcelException {
        try {
            this.access.invoke(target, methodName, value);
        } catch (ClassCastException e) {
            if (value instanceof Double) {//Double => Integer
                this.typeLoader(Integer.valueOf(NumberFormat.getInstance().format(Math.rint((Double) value))), methodName, target);
                return;
            }
            if (value instanceof Integer) {//Integer => String
                this.typeLoader(value.toString(), methodName, target);
                return;
            }
            if (value instanceof Boolean) {
                this.typeLoader(value.toString(), methodName, target);
                return;
            }
            throw new ExcelException("无法将单元格值类型放入对象,类型不匹配", e);
        }
    }

    /**
     * 读取行
     *
     * @param clazz
     * @param row
     * @param <T>
     * @return
     * @throws ExcelException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    protected <T> T loadRow(Class<T> clazz, Row row) throws ExcelException, InstantiationException, IllegalAccessException {
        T rs = clazz.newInstance();
        if (Map.class.isAssignableFrom(clazz)) {//Map集合
            this.loadMap(row, (Map<String, Object>) rs);
            return rs;
        }
        if (Collection.class.isAssignableFrom(clazz)) {//表示使用集合去装载数据,此时不记录field
            this.loadCollection(row, (Collection<Object>) rs);
            return rs;
        }
        if (this.access == null) {
            this.access = MethodAccess.get(clazz);
        }
        this.loadObject(row, rs);//对象
        return rs;
    }

    /**
     * 读取所有行
     *
     * @param clazz      数据类型
     * @param handlerRow 操作行
     * @param <T>
     * @throws ExcelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    protected <T> void loadRows(Class<T> clazz, HandlerRowA handlerRow) throws ExcelException, IllegalAccessException, InstantiationException {
        Iterator<Row> rows = this.sheet.iterator();
        int i = 0;
        int start = rowCursor;//开始读取的行号
        ArrayList<T> records = new ArrayList<>();
        while (rows.hasNext()) {
            if (i++ <= start) {//小于等于行游标的不读
                rows.next();
                continue;
            }
            rowCursor++;//读完一行游标下移
            T row = this.loadRow(clazz, rows.next());
            records.add(row);
            handlerRow.accept(row, records, rowCursor, records.size() - 1);
        }
        loadDatasList.add(records);
    }

    /**
     * 读取所有行
     *
     * @param clazz      数据类型
     * @param handlerRow 操作行,返回false不继续读取下一行
     * @param <T>
     * @throws ExcelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    protected <T> void loadRows(Class<T> clazz, HandlerRowB handlerRow) throws ExcelException, IllegalAccessException, InstantiationException {
        Iterator<Row> rows = this.sheet.iterator();
        int i = 0;
        int start = rowCursor;//开始读取的行号
        ArrayList<T> records = new ArrayList<>();
        while (rows.hasNext()) {
            if (i++ <= start) {//小于等于行游标的不读
                rows.next();
                continue;
            }
            rowCursor++;//读完一行游标下移
            T row = this.loadRow(clazz, rows.next());
            records.add(row);
            boolean goon = handlerRow.apply(row, records, rowCursor, records.size() - 1);
            if(!goon) {
                break;
            }
        }
        loadDatasList.add(records);
    }

    /**
     * 读取所有行
     *
     * @param clazz 数据类型
     * @param <T>
     * @throws ExcelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    protected <T> void loadRows(Class<T> clazz) throws ExcelException, IllegalAccessException, InstantiationException {
        Iterator<Row> rows = this.sheet.iterator();
        int i = 0;
        int start = rowCursor;//开始读取的行号
        ArrayList<T> records = new ArrayList<>();
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

    /**
     * 解析表头
     *
     * @param titles 表头合并单元格信息
     * @param rowSpan 占用行数
     */
    protected void parseExportTitles(Collection<MergeCell> titles, int rowSpan) throws ExcelException {
        Double maxRowIndex = Double.NEGATIVE_INFINITY;//无穷小
        Double minRowIndex = Double.POSITIVE_INFINITY;//无穷大
        for (MergeCell title : titles) {
            XSSFMergeCell mergeCell = (XSSFMergeCell) title;
            if (mergeCell.getEndRow() > maxRowIndex) {
                maxRowIndex = Double.valueOf(mergeCell.getEndRow());
            }
            if (mergeCell.getStartRow() < minRowIndex) {
                minRowIndex = Double.valueOf(mergeCell.getStartRow());
            }
        }
        //记录行号
        NumberFormat nf = NumberFormat.getInstance();
        int finalMaxRowIndex = Integer.parseInt(nf.format(maxRowIndex));
        int finalMinRowIndex = Integer.parseInt(nf.format(minRowIndex));
        int index = finalMinRowIndex + rowSpan - 1;
        setRowCursor(idx -> finalMaxRowIndex >= index ? finalMaxRowIndex : index);
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

    public <T> ExcelSheetImport setTitles(int rowSpan, ExcelTitleCellAbstract[][] excelTitles, Class<T> clazz) throws ExcelException {
        if (!(excelTitles instanceof XSSFExcelTitle[][])) {
            throw new ExportException("SXSSFExcelSheetExport setTitles excelTitles类型应该为XSSFExcelTitle[][]");
        }
        this.titleMergeCells = handlerExcelTitles(excelTitles);
        this.dataTitleMergeCells = this.searchDataTitleMergeCells(this.titleMergeCells);
        this.parseExportTitles(this.dataTitleMergeCells, rowSpan);
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
    public <T> ExcelSheetImport setColumnFields(int rowSpan, List<String> fields, Class<T> clazz) throws ExcelException {
        XSSFExcelTitle[][] excelTitles = new XSSFExcelTitle[1][fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            excelTitles[0][i] = new XSSFExcelTitle(fields.get(i));
        }
        return setTitles(rowSpan, excelTitles, clazz);
    }

    @Override
    public ExcelSheetImport setColumnFields(String... fields) throws ExcelException {
        return setColumnFields(Arrays.asList(fields), HashMap.class);
    }

    @Override
    public ExcelSheetImport setColumnFields(int rowSpan, String... fields) throws ExcelException {
        return setColumnFields(rowSpan, Arrays.asList(fields), HashMap.class);
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

    @Override
    public <T> SheetImportHandler readRows(Class<T> clazz, HandlerRowA handlerRow) throws ExcelException, InstantiationException, IllegalAccessException {
        this.loadRows(clazz, handlerRow);
        return this;
    }

    @Override
    public <T> SheetImportHandler readRows(Class<T> clazz, HandlerRowB handlerRow) throws ExcelException, InstantiationException, IllegalAccessException {
        this.loadRows(clazz, handlerRow);
        return this;
    }

    @Override
    public SheetImportHandler readRows() throws ExcelException, IllegalAccessException, InstantiationException {
        this.loadRows(this.clazz);
        return this;
    }

    @Override
    public SheetImportHandler readRows(HandlerRowA handlerRow) throws ExcelException, IllegalAccessException, InstantiationException {
        this.loadRows(this.clazz, handlerRow);
        return this;
    }

    @Override
    public SheetImportHandler readRows(HandlerRowB handlerRow) throws ExcelException, IllegalAccessException, InstantiationException {
        this.loadRows(this.clazz, handlerRow);
        return this;
    }

    @Override
    public <T> ArrayList<T> getReadData() {
        return (ArrayList<T>) this.loadDatasList.get(this.loadDatasList.size() - 1);
    }

    @Override
    public <T> ArrayList<T> getReadData(int index) {
        return (ArrayList<T>) this.loadDatasList.get(index);
    }

    @Override
    public <T> ArrayList<T> getAllReadData() {
        return (ArrayList<T>) this.loadDatasList;
    }

}
