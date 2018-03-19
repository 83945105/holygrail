package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.exception.ExportException;
import com.avalon.holygrail.excel.model.ExcelTitleCellAbstract;
import com.avalon.holygrail.excel.norm.*;
import com.avalon.holygrail.util.ClassUtil;
import com.avalon.holygrail.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

/**
 * SXSSFWorkbook SheetExportHandler
 * Created by 白超 on 2018/1/17.
 */
public class SXSSFExcelSheetExport extends SXSSFExcelWorkBookExport implements ExcelSheetExport {

    protected SXSSFSheet sheet;//当前数据表对象

    protected SXSSFExcelWorkBookExport ownerWorkBook;//所属工作簿对象

    protected List<MergeCell> titleMergeCells;//表头合并单元格信息

    protected LinkedList<MergeCell> dataTitleMergeCells;//与数据相关的表头信息

    protected int rowCursor = -1;//行游标,记录每次插入数据时的总起始行号

    protected int colCursor = -1;//列游标,记录每次插入数据时的总起始列号

    protected int totalDataSize;//数据记录总数

    public SXSSFExcelSheetExport(String sheetName, SXSSFExcelWorkBookExport ownerWorkBook) throws ExportException {
        super(ownerWorkBook.sxssfWorkbook);
        try {
            this.sheet = (SXSSFSheet) this.sxssfWorkbook.createSheet(sheetName);
        } catch (IllegalArgumentException e) {
            throw new ExportException("已经存在名为:" + sheetName + "的sheet", e);
        }
        this.ownerWorkBook = ownerWorkBook;
    }

    public SXSSFExcelSheetExport(SXSSFWorkbook workbook, String sheetName, SXSSFExcelWorkBookExport ownerWorkBook) throws ExportException {
        super(ownerWorkBook.sxssfWorkbook);
        try {
            this.sheet = (SXSSFSheet) this.sxssfWorkbook.createSheet(sheetName);
        } catch (IllegalArgumentException e) {
            throw new ExportException("已经存在名为:" + sheetName + "的sheet", e);
        }
        this.ownerWorkBook = ownerWorkBook;
    }

    /**
     * 获取行
     * 存在获取,不存在创建
     *
     * @param startRowNum
     */
    protected SXSSFRow findRow(int startRowNum) throws ExcelException {
        int rowIndex = startRowNum - 1;
        SXSSFRow row = (SXSSFRow) this.sheet.getRow(rowIndex);
        if (row == null) {
/*            if (this.rowCursor >= rowIndex) {
                throw new ExportException("SXSSFExcelSheetExport parseExportTitles rowCursor位置异常");
            }*/
            row = (SXSSFRow) this.sheet.createRow(rowIndex);
            this.setRowCursor(rowCursor -> rowIndex);
        }
        return row;
    }

    /**
     * 获取单元格
     * 存在获取,不存在创建
     *
     * @param row
     * @param startColumnNum
     */
    protected SXSSFCell findCell(SXSSFRow row, int startColumnNum) throws ExcelException {
        int columnIndex = startColumnNum - 1;
        SXSSFCell cell = (SXSSFCell) row.getCell(columnIndex);
        if (cell == null) {
            cell = (SXSSFCell) row.createCell(columnIndex);
        }
        return cell;
    }

    //重写表头构建方法,主要关联了行游标和列游标
    @Override
    public SXSSFMergeCell buildTitleMergeCell(ExcelTitleCellAbstract excelTitle, int startRow, int endRow, int startCol, int endCol) throws ExcelException {
        SXSSFMergeCell mergeCell = new SXSSFMergeCell(this.rowCursor + startRow + 1, this.colCursor + startCol + 1, endRow - startRow + 1, endCol - startCol + 1);
        excelTitle.copyCellOptionSelective(mergeCell);//设置属性
        excelTitle.copyCellStyleByName(mergeCell);//设置样式
        return mergeCell;
    }

    /**
     * 获取数据校验对象
     */
    protected DataValidation getDataValidation(SXSSFMergeCell mergeCell) {
        if (mergeCell.getType() == CellOption.CellType.COMBOBOX) {
            DataValidationHelper helper = this.sheet.getDataValidationHelper();
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(mergeCell.getStartRowNum() - 1, mergeCell.getEndRowNum() - 1, mergeCell.getStartColNum() - 1, mergeCell.getEndColNum() - 1);
            DataValidationConstraint constraint = helper.createExplicitListConstraint(mergeCell.getOptions());
            DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
            if (dataValidation instanceof XSSFDataValidation) {
                dataValidation.setSuppressDropDownArrow(true);
                dataValidation.setShowErrorBox(true);
            } else {
                dataValidation.setSuppressDropDownArrow(true);
            }
            return dataValidation;
        }
        return null;
    }

    /**
     * 构建单元格
     *
     * @param mergeCell 单元格相关信息
     */
    protected void buildCell(SXSSFMergeCell mergeCell) throws ExcelException {
        if (!mergeCell.isWriteEmpty() && StringUtil.isEmpty(mergeCell.getValue())) {
            //不允许写入空值且当前值为空
            return;
        }
        for (int i = 0; i < mergeCell.getRowSpan(); i++) {
            SXSSFRow row = this.findRow(mergeCell.getStartRowNum() + i);
            for (int j = 0; j < mergeCell.getColSpan(); j++) {
                SXSSFCell cell = this.findCell(row, mergeCell.getStartColNum() + j);
                DataValidation dataValidation = this.getDataValidation(mergeCell);
                if (dataValidation != null) {
                    this.sheet.addValidationData(dataValidation);
                }
                XSSFCellStyle cellStyle = (XSSFCellStyle) this.sxssfWorkbook.createCellStyle();
                SXSSFLoader sxssfLoader = new SXSSFLoader(this.sheet, cell, cellStyle);
                //设置属性
                mergeCell.copyCellOptionSelective(sxssfLoader);
                //设置样式
                mergeCell.copyCellStyleByName(sxssfLoader);
            }
        }
        //添加合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(mergeCell.getStartRowNum() - 1, mergeCell.getEndRowNum() - 1, mergeCell.getStartColNum() - 1, mergeCell.getEndColNum() - 1);
        this.sheet.addMergedRegion(cellRangeAddress);
    }

    /**
     * 解析表头
     *
     * @param titles 表头合并单元格信息
     */
    protected void parseExportTitles(Collection<MergeCell> titles) throws ExcelException {
        for (MergeCell title : titles) {
            SXSSFMergeCell mergeCell = (SXSSFMergeCell) title;
            //开始处理合并单元格
            this.buildCell(mergeCell);
        }
    }

    /**
     * 设置合并单元格宽度
     *
     * @param mergeCell
     */
    protected void setMergeCellColumnWidth(SXSSFMergeCell mergeCell) {
        int width = mergeCell.getWidth() * 256 / mergeCell.getColSpan();
        for (int i = mergeCell.getStartColNum(); i <= mergeCell.getEndColNum(); i++) {
            this.setColumnWidth(i - 1, width);
        }
    }

    protected <T> void parseRecord(T record) throws ExcelException {
        if (record instanceof Map) {
            this.parseMap((Map<String, Object>) record);
            return;
        }
        this.parseObject(record);
    }

    /**
     * 格式化单元格
     *
     * @param <T>
     */
    @FunctionalInterface
    public interface FormatterCell<T> {

        /**
         * 格式化单元格
         *
         * @param value       当前单元格值
         * @param record      当前行数据
         * @param cellHandler 操作单元格
         * @param field       单元格所在列值
         * @param rowCursor   当前行游标
         * @param index       当前数据在数据集合中的下标
         * @return 你要设置的单元格值
         */
        Object apply(Object value, T record, CellHandler cellHandler, String field, int rowCursor, int index) throws ExportException;
    }

    protected <T> void parseRecord(T record, int index, FormatterCell<T> formatter) throws ExcelException {
        if (record instanceof Map) {
            this.parseMap((Map<String, Object>) record, index, (FormatterCell<Map<String, Object>>) formatter);
            return;
        }
        this.parseObject(record, index, formatter);
    }

    protected void parseMap(Map<String, Object> record) throws ExcelException {
        int startRow = this.rowCursor + 2;
        for (MergeCell titleMergeCell : this.dataTitleMergeCells) {
            SXSSFMergeCell tMergeCell = (SXSSFMergeCell) titleMergeCell;
            //创建数据单元格,默认开始行使用当前游标+2、默认开始列与title一致，默认占用一行、占用列与title一致
            SXSSFMergeCell mergeCell = new SXSSFMergeCell(startRow, tMergeCell.getStartColNum(), 1, tMergeCell.getColSpan());
            mergeCell.setField(tMergeCell.getField());
            mergeCell.setWriteEmpty(tMergeCell.isWriteEmpty());
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                if (!entry.getKey().equals(mergeCell.getField())) {
                    continue;
                }
                try {
                    Object value = entry.getValue();
                    mergeCell.setValue(value == null ? "" : value.toString());
                } catch (Exception e) {
                    mergeCell.setValue("");
                }
                this.buildCell(mergeCell);
                break;
            }
        }
    }

    protected void parseMap(Map<String, Object> record, int index, FormatterCell<Map<String, Object>> formatter) throws ExcelException {
        int startRow = this.rowCursor + 2;
        for (MergeCell titleMergeCell : this.dataTitleMergeCells) {
            SXSSFMergeCell tMergeCell = (SXSSFMergeCell) titleMergeCell;
            //创建数据单元格,默认开始行使用当前游标+2、默认开始列与title一致，默认占用一行、占用列与title一致
            SXSSFMergeCell mergeCell = new SXSSFMergeCell(startRow, tMergeCell.getStartColNum(), 1, tMergeCell.getColSpan());
            mergeCell.setField(tMergeCell.getField());
            mergeCell.setWriteEmpty(tMergeCell.isWriteEmpty());
            int i = 0;
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                boolean last = i++ == record.size() - 1;
                boolean equal = entry.getKey().equals(mergeCell.getField());
                if (!equal && !last) {//不等于且不是最后一条,继续
                    continue;
                }
                Object value;
                if (equal) {//等于
                    try {
                        value = entry.getValue();
                    } catch (Exception e) {
                        value = "";
                    }
                } else {//最后一条依然不等于,给个默认值""
                    value = "";
                }
                //格式化
                value = formatter.apply(value, record, mergeCell, mergeCell.getField(), this.rowCursor, index);
                mergeCell.setValue(value);
                this.buildCell(mergeCell);
                break;
            }
        }
    }

    protected <T> void parseObject(T record) throws ExcelException {
        int startRow = this.rowCursor + 2;
        ArrayList<Field> fs = ClassUtil.getAllFields(record.getClass());
        for (MergeCell titleMergeCell : this.dataTitleMergeCells) {
            SXSSFMergeCell tMergeCell = (SXSSFMergeCell) titleMergeCell;
            //创建数据单元格,默认开始行使用当前游标+2、默认开始列与title一致，默认占用一行、占用列与title一致
            SXSSFMergeCell mergeCell = new SXSSFMergeCell(startRow, tMergeCell.getStartColNum(), 1, tMergeCell.getColSpan());
            mergeCell.setField(tMergeCell.getField());
            mergeCell.setWriteEmpty(tMergeCell.isWriteEmpty());
            for (Field f : fs) {
                f.setAccessible(true);
                if (!f.getName().equals(mergeCell.getField())) {
                    continue;
                }
                try {
                    Object value = new PropertyDescriptor(f.getName(), record.getClass()).getReadMethod().invoke(record);
                    //Object value = this.access.invoke(record, ClassUtil.getGetterMethodName(f.getName(), null));
                    mergeCell.setValue(value == null ? "" : value.toString());
                } catch (Exception e) {
                    mergeCell.setValue("");
                }
                this.buildCell(mergeCell);
                break;
            }
        }
    }

    protected <T> void parseObject(T record, int index, FormatterCell<T> formatter) throws ExcelException {
        int startRow = this.rowCursor + 2;
        ArrayList<Field> fs = ClassUtil.getAllFields(record.getClass());
        for (MergeCell titleMergeCell : this.dataTitleMergeCells) {
            SXSSFMergeCell tMergeCell = (SXSSFMergeCell) titleMergeCell;
            //创建数据单元格,默认开始行使用当前游标+2、默认开始列与title一致，默认占用一行、占用列与title一致
            SXSSFMergeCell mergeCell = new SXSSFMergeCell(startRow, tMergeCell.getStartColNum(), 1, tMergeCell.getColSpan());
            mergeCell.setField(tMergeCell.getField());
            mergeCell.setWriteEmpty(tMergeCell.isWriteEmpty());
            for (int i = 0; i < fs.size(); i++) {
                Field f = fs.get(i);
                f.setAccessible(true);
                boolean last = i == fs.size() - 1;
                boolean equal = f.getName().equals(mergeCell.getField());
                if (!equal && !last) {
                    continue;
                }
                Object value;
                if (equal) {//等于
                    try {
                        value = new PropertyDescriptor(f.getName(), record.getClass()).getReadMethod().invoke(record);
                        //value = this.access.invoke(record, ClassUtil.getGetterMethodName(f.getName(), null));
                    } catch (Exception e) {
                        value = "";
                    }
                } else {//最后一条依然不等于,给个默认值""
                    value = "";
                }
                //格式化
                value = formatter.apply(value, record, mergeCell, mergeCell.getField(), this.rowCursor, index);
                mergeCell.setValue(value);
                this.buildCell(mergeCell);
                break;
            }
        }
    }

//    private Class cls = Object.class;
//    private MethodAccess access = null;

    /**
     * 解析数据
     *
     * @param records 数据集合
     * @param <T>     数据类型
     */
    protected <T> void parseExportData(Collection<T> records) throws ExcelException {
        this.totalDataSize += records.size();
        //找到了表头对应的数据
        for (T record : records) {
/*            if(record.getClass() != this.cls || this.access == null) {
                this.cls = record.getClass();
                this.access = MethodAccess.get(this.cls);
            }*/
            this.parseRecord(record);
        }
    }

    @Override
    public void export(String outPath) throws IOException {
        super.export(outPath);
    }

    /**
     * 解析数据
     *
     * @param records   数据集合
     * @param formatter 格式化函数,接收5个参数,分别为 当前当前数据对象record、单元格信息、当前列值、游标、当前记录下标,需要返回要设置的单元格的值
     * @param <T>       数据类型
     */
    protected <T> void parseExportData(Collection<T> records, FormatterCell<T> formatter) throws ExcelException {
        this.totalDataSize += records.size();
        int index = 0;
        for (T record : records) {
/*            if(record.getClass() != this.cls || this.access == null) {
                this.cls = record.getClass();
                this.access = MethodAccess.get(this.cls);
            }*/
            this.parseRecord(record, index, formatter);
            index++;
        }
    }

    @Override
    public ExcelSheetExport setRowCursor(Function<Integer, Integer> handler) {
        this.rowCursor = handler.apply(this.rowCursor);
        return this;
    }

    @Override
    public ExcelSheetExport setColCursor(Function<Integer, Integer> handler) {
        this.colCursor = handler.apply(this.colCursor);
        return this;
    }

    @Override
    public SXSSFExcelWorkBookExport getOwnerWorkBook() {
        return this.ownerWorkBook;
    }

    @Override
    public ExcelSheetExport parseTitlesJson(String titlesJson, boolean exportTitles) throws ExcelException {
        SXSSFExcelTitle[][] excelTitles = this.parseCellsJson(titlesJson);
        return setTitles(excelTitles, exportTitles);
    }

    @Override
    public ExcelSheetExport parseTitlesJson(InputStream inputStream, boolean exportTitles) throws IOException, ExcelException {
        SXSSFExcelTitle[][] excelTitles = (SXSSFExcelTitle[][]) this.parseCellsJson(inputStream);
        return setTitles(excelTitles, exportTitles);
    }

    @Override
    public ExcelSheetExport parseTitlesJson(File file, boolean exportTitles) throws IOException, ExcelException {
        SXSSFExcelTitle[][] excelTitles = (SXSSFExcelTitle[][]) this.parseCellsJson(file);
        return setTitles(excelTitles, exportTitles);
    }

    @Override
    public ExcelSheetExport setTitles(ExcelTitleCellAbstract[][] excelTitles, boolean exportTitles) throws ExcelException {
        if (!(excelTitles instanceof SXSSFExcelTitle[][])) {
            throw new ExportException("SXSSFExcelSheetExport setTitles excelTitles类型应该为SXSSFExcelTitle[][]");
        }
        this.titleMergeCells = handlerExcelTitles(excelTitles);
        this.dataTitleMergeCells = this.searchDataTitleMergeCells(this.titleMergeCells);
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
    public ExcelSheetExport setColumnFields(List<String> fields) throws ExcelException {
        SXSSFExcelTitle[][] excelTitles = new SXSSFExcelTitle[1][fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            excelTitles[0][i] = new SXSSFExcelTitle(fields.get(i));
        }
        return setTitles(excelTitles, false);
    }

    @Override
    public ExcelSheetExport insertPicture(InputStream inputStream, ExcelWorkBook.PictureType pictureType, int dx1, int dy1, int dx2, int dy2, int col1, int row1, int col2, int row2) throws IOException {

        BufferedImage bufferedImage;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bufferedImage = ImageIO.read(inputStream);
        ImageIO.write(bufferedImage, pictureType.suffix, byteArrayOutputStream);

        XSSFDrawing drawing = (XSSFDrawing) this.sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = new XSSFClientAnchor(dx1, dy1, dx2, dy2, (short) col1, row1, (short) col2, row2);
        anchor.setAnchorType(3);

        drawing.createPicture(anchor, this.sxssfWorkbook.addPicture(byteArrayOutputStream.toByteArray(), pictureType.value));

        return this;
    }

    @Override
    public void setColumnWidth(int columnIndex, int width) {
        this.sheet.setColumnWidth(columnIndex, width);
    }

    @Override
    public <T> ExcelSheetExport importData(Collection<T> records) throws ExcelException {
        this.parseExportData(records);
        return this;
    }

    @Override
    public <T> ExcelSheetExport importData(Collection<T> records, FormatterCell<T> formatter) throws ExcelException {
        this.parseExportData(records, formatter);
        return this;
    }

    @Override
    public int getTotalDataSize() {
        return this.totalDataSize;
    }

}