package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.exception.ExportException;
import com.avalon.holygrail.excel.model.ExcelTitleCellAbstract;
import com.avalon.holygrail.excel.model.XSSFExcelTitle;
import com.avalon.holygrail.excel.model.XSSFMergeCell;
import com.avalon.holygrail.excel.norm.ExcelSheetImport;
import com.avalon.holygrail.excel.norm.ExcelWorkBookImport;
import com.avalon.holygrail.excel.norm.MergeCell;
import com.avalon.holygrail.excel.norm.Sheet;
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
 * XSSFWorkBook
 * Created by 白超 on 2018/1/24.
 */
public class XSSFExcelSheetImport extends XSSFExcelWorkBookImport implements ExcelSheetImport {

    protected XSSFSheet sheet;//当前数据表对象

    protected XSSFExcelWorkBookImport ownerWorkBook;//所属工作簿对象

    protected List<MergeCell> titleMergeCells;//表头合并单元格信息

    protected LinkedList<MergeCell> dataTitleMergeCells = new LinkedList<>();//与数据相关的表头信息

    protected int rowCursor = -1;//行游标,记录读取起始行号

    protected int colCursor = -1;//列游标,记录读取起始列号

    protected int physicalNumberOfRows;//物理行数

    protected Class<?> defaultClass = ArrayList.class;//默认数据容器

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
            if (j < this.colCursor) {//小于列游标不读
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
            XSSFMergeCell tMergeCell = (XSSFMergeCell) this.searchMergeCell(this.dataTitleMergeCells, cell.getColumnIndex());
            if (tMergeCell == null) {
                container.put(Sheet.getColumnName(cell.getColumnIndex() + 1) + (cell.getRowIndex() + 1), xssfLoader.getValue());
            } else {
                container.put(tMergeCell.getField(), xssfLoader.getValue());
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
            container.add(xssfLoader.getValue());
        });
    }

    private Map<String, String> setMethodNames = new HashMap<>();

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
            XSSFMergeCell tMergeCell = (XSSFMergeCell) this.searchMergeCell(this.dataTitleMergeCells, cell.getColumnIndex());
            if (tMergeCell != null) {
                String field = tMergeCell.getField();
                String methodName = this.setMethodNames.get(field);
                if (methodName == null) {
                    methodName = ClassUtil.getSetterMethodName(field);
                    this.setMethodNames.put(field, methodName);
                }
                this.typeLoader(target, methodName, xssfLoader.getValue());
            }
        });
    }

    /**
     * 类型装载器
     *
     * @param target
     * @param methodName
     * @param value
     * @param <T>
     * @throws ExcelException
     */
    protected <T> void typeLoader(T target, String methodName, Object value) throws ExcelException {
        try {
            this.access.invoke(target, methodName, value);
        } catch (ClassCastException e) {
            if (value instanceof Double) {//Double => Integer
                this.typeLoader(target, methodName, Integer.valueOf(NumberFormat.getInstance().format(Math.rint((Double) value))));
                return;
            }
            if (value instanceof Integer) {//Integer => String
                this.typeLoader(target, methodName, value.toString());
                return;
            }
            if (value instanceof Boolean) {
                this.typeLoader(target, methodName, value.toString());
                return;
            }
            throw new ExcelException("无法将单元格类型值注入对象,类型不匹配", e);
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
        //设置行游标
        this.setRowCursor(idx -> row.getRowNum());
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
    protected <T> void loadRows(Class<T> clazz, HandlerRowA<T> handlerRow) throws ExcelException, IllegalAccessException, InstantiationException {
        Iterator<Row> rows = this.sheet.iterator();
        int i = 0;
        int start = this.rowCursor;//开始读取的行号
        ArrayList<T> records = new ArrayList<>();
        while (rows.hasNext()) {
            if (i++ <= start) {//小于等于行游标的不读
                rows.next();
                continue;
            }
            T row = this.loadRow(clazz, rows.next());
            records.add(row);
            handlerRow.accept(row, records, this.rowCursor + 1, records.size() - 1);
        }
        this.loadDatasList.add(records);
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
    protected <T> void loadRows(Class<T> clazz, HandlerRowB<T> handlerRow) throws ExcelException, IllegalAccessException, InstantiationException {
        Iterator<Row> rows = this.sheet.iterator();
        int i = 0;
        int start = this.rowCursor;//开始读取的行号
        ArrayList<T> records = new ArrayList<>();
        while (rows.hasNext()) {
            if (i++ <= start) {//小于等于行游标的不读
                rows.next();
                continue;
            }
            T row = this.loadRow(clazz, rows.next());
            records.add(row);
            boolean goon = handlerRow.apply(row, records, this.rowCursor + 1, records.size() - 1);
            if (!goon) {
                break;
            }
        }
        this.loadDatasList.add(records);
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
        int start = this.rowCursor;//开始读取的行号
        ArrayList<T> records = new ArrayList<>();
        while (rows.hasNext()) {
            if (i++ <= start) {//小于等于行游标的不读
                rows.next();
                continue;
            }
            T row = this.loadRow(clazz, rows.next());
            records.add(row);
        }
        this.loadDatasList.add(records);
    }

    /**
     * 解析表头
     *
     * @param titles 表头合并单元格信息
     */
    protected void parseExportTitles(Collection<MergeCell> titles) throws ExcelException {
        int maxRowNum = this.rowCursor + 1;
        for (MergeCell title : titles) {
            XSSFMergeCell mergeCell = (XSSFMergeCell) title;
            if (mergeCell.getEndRowNum() > maxRowNum) {
                maxRowNum = mergeCell.getEndRowNum();
            }
        }
        //记录行号
        int finalMaxRowIndex = maxRowNum - 1;
        setRowCursor(idx -> finalMaxRowIndex);
    }

    /**
     * 解析表头
     *
     * @param titles  表头合并单元格信息
     * @param rowSpan 占用行数
     */
    protected void parseExportTitles(Collection<MergeCell> titles, int rowSpan) throws ExcelException {
        Double maxRowNum = Double.NEGATIVE_INFINITY;//无穷小
        Double minRowNum = Double.POSITIVE_INFINITY;//无穷大
        for (MergeCell title : titles) {
            XSSFMergeCell mergeCell = (XSSFMergeCell) title;
            if (mergeCell.getEndRowNum() > maxRowNum) {
                maxRowNum = Double.valueOf(mergeCell.getEndRowNum());
            }
            if (mergeCell.getStartRowNum() < minRowNum) {
                minRowNum = Double.valueOf(mergeCell.getStartRowNum());
            }
        }
        //记录行号
        NumberFormat nf = NumberFormat.getInstance();
        int finalMaxRowIndex = Integer.parseInt(nf.format(maxRowNum)) - 1;
        int finalMinRowIndex = Integer.parseInt(nf.format(minRowNum)) - 1;
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
        this.defaultClass = clazz;
        return this;
    }

    public <T> ExcelSheetImport setTitles(int rowSpan, ExcelTitleCellAbstract[][] excelTitles, Class<T> clazz) throws ExcelException {
        if (!(excelTitles instanceof XSSFExcelTitle[][])) {
            throw new ExportException("SXSSFExcelSheetExport setTitles excelTitles类型应该为XSSFExcelTitle[][]");
        }
        this.titleMergeCells = handlerExcelTitles(excelTitles);
        this.dataTitleMergeCells = this.searchDataTitleMergeCells(this.titleMergeCells);
        this.parseExportTitles(this.dataTitleMergeCells, rowSpan);
        this.defaultClass = clazz;
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
    public <T> ExcelSheetImport readRows(Class<T> clazz) throws ExcelException, InstantiationException, IllegalAccessException {
        this.loadRows(clazz);
        return this;
    }

    @Override
    public <T> ExcelSheetImport readRows(Class<T> clazz, HandlerRowA<T> handlerRow) throws ExcelException, InstantiationException, IllegalAccessException {
        this.loadRows(clazz, handlerRow);
        return this;
    }

    @Override
    public <T> ExcelSheetImport readRows(Class<T> clazz, HandlerRowB<T> handlerRow) throws ExcelException, InstantiationException, IllegalAccessException {
        this.loadRows(clazz, handlerRow);
        return this;
    }

    @Override
    public ExcelSheetImport readRows() throws ExcelException, IllegalAccessException, InstantiationException {
        this.loadRows(this.defaultClass);
        return this;
    }

    @Override
    public ExcelSheetImport readRows(HandlerRowA handlerRow) throws ExcelException, IllegalAccessException, InstantiationException {
        this.loadRows(this.defaultClass, handlerRow);
        return this;
    }

    @Override
    public ExcelSheetImport readRows(HandlerRowB handlerRow) throws ExcelException, IllegalAccessException, InstantiationException {
        this.loadRows(this.defaultClass, handlerRow);
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
