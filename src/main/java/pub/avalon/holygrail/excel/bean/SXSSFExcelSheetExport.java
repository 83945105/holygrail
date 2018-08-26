package pub.avalon.holygrail.excel.bean;

import pub.avalon.holygrail.excel.exception.ExcelException;
import pub.avalon.holygrail.excel.exception.ExportException;
import pub.avalon.holygrail.excel.model.BaseCell;
import pub.avalon.holygrail.excel.model.BaseExcelTitleCell;
import pub.avalon.holygrail.excel.norm.CellOption;
import pub.avalon.holygrail.excel.norm.ExcelCellHandler;
import pub.avalon.holygrail.excel.norm.ExcelSheetExport;
import pub.avalon.holygrail.excel.norm.ExcelWorkBook;
import pub.avalon.holygrail.utils.ClassUtil;
import pub.avalon.holygrail.utils.StringUtil;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

/**
 * SXSSFWorkbook SheetExportHandler
 *
 * @author 白超
 * @date 2018/1/17
 */
public class SXSSFExcelSheetExport extends SXSSFExcelWorkBookExport implements ExcelSheetExport {

    /**
     * 当前数据表对象
     */
    protected SXSSFSheet sheet;

    /**
     * 所属工作簿对象
     */
    protected SXSSFExcelWorkBookExport ownerWorkBook;

    /**
     * 表头单元格信息
     */
    protected List<BaseExcelTitleCell> titleCells;

    /**
     * 与数据相关的表头信息
     */
    protected LinkedList<BaseExcelTitleCell> dataTitleCells;

    /**
     * 行游标,记录每次插入数据时的总起始行号
     */
    protected int rowCursor = -1;

    /**
     * 列游标,记录每次插入数据时的总起始列号
     */
    protected int colCursor = -1;

    /**
     * 数据记录总数
     */
    protected int totalDataSize;

    /**
     * 默认的单元格样式
     */
    protected CellStyleProxy defaultCellStyle;

    /**
     * 装载器
     */
    protected SXSSFLoader sxssfLoader;

    public SXSSFExcelSheetExport(String sheetName, SXSSFExcelWorkBookExport ownerWorkBook) throws ExportException {
        super(ownerWorkBook.sxssfWorkbook);
        try {
            this.sheet = (SXSSFSheet) this.sxssfWorkbook.createSheet(sheetName);
        } catch (IllegalArgumentException e) {
            throw new ExportException("已经存在名为:" + sheetName + "的sheet", e);
        }
        this.ownerWorkBook = ownerWorkBook;
        this.defaultCellStyle = new CellStyleProxy(this.ownerWorkBook.sxssfWorkbook.createCellStyle(),
                new FontProxy(this.ownerWorkBook.sxssfWorkbook.createFont()));
        this.sxssfLoader = new SXSSFLoader(this.ownerWorkBook.sxssfWorkbook);
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
    protected org.apache.poi.xssf.streaming.SXSSFCell findCell(SXSSFRow row, int startColumnNum) throws ExcelException {
        int columnIndex = startColumnNum - 1;
        org.apache.poi.xssf.streaming.SXSSFCell cell = (org.apache.poi.xssf.streaming.SXSSFCell) row.getCell(columnIndex);
        if (cell == null) {
            cell = (org.apache.poi.xssf.streaming.SXSSFCell) row.createCell(columnIndex);
        }
        return cell;
    }

    /**
     * 重写表头构建方法,主要关联了行游标和列游标
     *
     * @param excelTitle
     * @param startRow
     * @param endRow
     * @param startCol
     * @param endCol
     * @return
     */
    @Override
    public BaseExcelTitleCell buildExcelTitleCell(BaseExcelTitleCell excelTitle, int startRow, int endRow, int startCol, int endCol) {
        excelTitle.setStartRowNum(this.rowCursor + startRow + 1);
        excelTitle.setStartColNum(this.colCursor + startCol + 1);
        return excelTitle;
    }

    /**
     * 获取数据校验对象
     */
    protected DataValidation getDataValidation(BaseCell cell) {
        if (cell.getCellType() == CellOption.CellType.COMBOBOX && cell.getCellOptions().length > 0) {
            DataValidationHelper helper = this.sheet.getDataValidationHelper();
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(cell.getStartRowNum() - 1, cell.getEndRowNum() - 1, cell.getStartColNum() - 1, cell.getEndColNum() - 1);
            DataValidationConstraint constraint = helper.createExplicitListConstraint(cell.getCellOptions());
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
     * @param cell 单元格相关信息
     */
    protected void buildCell(BaseCell cell) throws ExcelException {
        if (!cell.isWriteEmpty() && StringUtil.isEmpty(cell.getCellValue())) {
            //不允许写入空值且当前值为空
            return;
        }
        for (int i = 0; i < cell.getRowSpan(); i++) {
            SXSSFRow row = this.findRow(cell.getStartRowNum() + i);
            for (int j = 0; j < cell.getColSpan(); j++) {
                org.apache.poi.xssf.streaming.SXSSFCell poiCell = this.findCell(row, cell.getStartColNum() + j);
                DataValidation dataValidation = this.getDataValidation(cell);
                if (dataValidation != null) {
                    this.sheet.addValidationData(dataValidation);
                }
                sxssfLoader.loadCell(cell, poiCell);
            }
        }

        if (!cell.isMergeCell()) {
            //同一单元格,不用合并
            return;
        }
        CellRangeAddress cellRangeAddress = new CellRangeAddress(
                cell.getStartRowNum() - 1,
                cell.getEndRowNum() - 1,
                cell.getStartColNum() - 1,
                cell.getEndColNum() - 1);
        this.sheet.addMergedRegion(cellRangeAddress);
    }

    /**
     * 解析表头
     *
     * @param titles 表头单元格信息
     */
    protected void parseExportTitles(Collection<BaseExcelTitleCell> titles) throws ExcelException {
        for (BaseExcelTitleCell title : titles) {
            BaseExcelTitleCell titleCell = title;
            //开始处理表头
            this.buildCell(titleCell);
        }
    }

    /**
     * 设置表头单元格宽度
     *
     * @param titleCell
     */
    protected void setTitleCellColumnWidth(BaseExcelTitleCell titleCell) {
        int width = titleCell.getWidth() * 256 / titleCell.getColSpan();
        for (int i = titleCell.getStartColNum(); i <= titleCell.getEndColNum(); i++) {
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
         * @throws ExportException
         */
        Object apply(Object value, T record, ExcelCellHandler cellHandler, String field, int rowCursor, int index) throws ExportException;
    }

    @SuppressWarnings("unchecked")
    protected <T> void parseRecord(T record, int index, FormatterCell<T> formatter) throws ExcelException {
        if (record instanceof Map) {
            this.parseMap((Map<String, Object>) record, index, (FormatterCell<Map<String, Object>>) formatter);
            return;
        }
        this.parseObject(record, index, formatter);
    }

    protected void parseMap(Map<String, Object> record) throws ExcelException {
        int startRow = this.rowCursor + 2;
        for (BaseExcelTitleCell titleCell : this.dataTitleCells) {
            //创建数据单元格,默认开始行使用当前游标+2、默认开始列与title一致，默认占用一行、占用列与title一致
            SXSSFCell cell = new SXSSFCell(this.getOwnerWorkBook(), this.defaultCellStyle, startRow, titleCell.getStartColNum(), 1, titleCell.getColSpan());
            cell.setField(titleCell.getField());
            cell.setWriteEmpty(titleCell.isWriteEmpty());
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                if (!entry.getKey().equals(cell.getField())) {
                    continue;
                }
                try {
                    Object value = entry.getValue();
                    cell.setValue(value == null ? "" : value.toString());
                } catch (Exception e) {
                    cell.setValue("");
                }
                this.buildCell(cell);
                break;
            }
        }
    }

    protected void parseMap(Map<String, Object> record, int index, FormatterCell<Map<String, Object>> formatter) throws ExcelException {
        int startRow = this.rowCursor + 2;
        for (BaseExcelTitleCell titleCell : this.dataTitleCells) {
            //创建数据单元格,默认开始行使用当前游标+2、默认开始列与title一致，默认占用一行、占用列与title一致
            SXSSFCell cell = new SXSSFCell(this.getOwnerWorkBook(), this.defaultCellStyle, startRow, titleCell.getStartColNum(), 1, titleCell.getColSpan());
            cell.setField(titleCell.getField());
            cell.setWriteEmpty(titleCell.isWriteEmpty());
            int i = 0;
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                boolean last = i++ == record.size() - 1;
                boolean equal = entry.getKey().equals(cell.getField());
                //不等于且不是最后一条,继续
                if (!equal && !last) {
                    continue;
                }
                Object value;
                //等于
                if (equal) {
                    try {
                        value = entry.getValue();
                    } catch (Exception e) {
                        value = "";
                    }
                } else {//最后一条依然不等于,给个默认值""
                    value = "";
                }
                //格式化
                value = formatter.apply(value, record, cell, cell.getField(), this.rowCursor, index);
                cell.setValue(value);
                this.buildCell(cell);
                break;
            }
        }
    }

    protected <T> void parseObject(T record) throws ExcelException {
        int startRow = this.rowCursor + 2;
        ArrayList<Field> fs = ClassUtil.getAllFields(record.getClass());
        for (BaseExcelTitleCell titleCell : this.dataTitleCells) {
            //创建数据单元格,默认开始行使用当前游标+2、默认开始列与title一致，默认占用一行、占用列与title一致
            SXSSFCell cell = new SXSSFCell(this.getOwnerWorkBook(), this.defaultCellStyle, startRow, titleCell.getStartColNum(), 1, titleCell.getColSpan());
            cell.setField(titleCell.getField());
            cell.setWriteEmpty(titleCell.isWriteEmpty());
            for (int i = 0; i < fs.size(); i++) {
                Field f = fs.get(i);
                f.setAccessible(true);
                boolean last = i == fs.size() - 1;
                boolean equal = f.getName().equals(cell.getField());
                if (!equal && !last) {
                    continue;
                }
                Object value;
                if (equal) {
                    try {
                        value = new PropertyDescriptor(f.getName(), record.getClass()).getReadMethod().invoke(record);
                    } catch (Exception e) {
                        value = "";
                    }
                } else {
                    //能进入这里,说明已经是最后一个属性了,但是还是没找到orz...尝试获取属性对应get方法
                    Method getMethod = ClassUtil.getMethod(record.getClass(), ClassUtil.getGetterMethodName(cell.getField(), false));
                    if (getMethod == null) {
                        getMethod = ClassUtil.getMethod(record.getClass(), ClassUtil.getGetterMethodName(cell.getField(), true));
                    }
                    if (getMethod != null) {
                        try {
                            value = getMethod.invoke(record);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            value = "";
                        }
                    } else {
                        value = "";
                    }
                }
                cell.setValue(value == null ? "" : value);
                this.buildCell(cell);
                break;
            }
        }
    }

    protected <T> void parseObject(T record, int index, FormatterCell<T> formatter) throws ExcelException {
        int startRow = this.rowCursor + 2;
        ArrayList<Field> fs = ClassUtil.getAllFields(record.getClass());
        for (BaseExcelTitleCell titleCell : this.dataTitleCells) {
            //创建数据单元格,默认开始行使用当前游标+2、默认开始列与title一致，默认占用一行、占用列与title一致
            SXSSFCell cell = new SXSSFCell(this.getOwnerWorkBook(), this.defaultCellStyle, startRow, titleCell.getStartColNum(), 1, titleCell.getColSpan());
            cell.setField(titleCell.getField());
            cell.setWriteEmpty(titleCell.isWriteEmpty());
            for (int i = 0; i < fs.size(); i++) {
                Field f = fs.get(i);
                f.setAccessible(true);
                boolean last = i == fs.size() - 1;
                boolean equal = f.getName().equals(cell.getField());
                if (!equal && !last) {
                    continue;
                }
                Object value;
                //等于
                if (equal) {
                    try {
                        value = new PropertyDescriptor(f.getName(), record.getClass()).getReadMethod().invoke(record);
                    } catch (Exception e) {
                        value = "";
                    }
                } else {//最后一条依然不等于,给个默认值""
                    //能进入这里,说明已经是最后一个属性了,但是还是没找到orz...尝试获取属性对应get方法
                    Method getMethod = ClassUtil.getMethod(record.getClass(), ClassUtil.getGetterMethodName(cell.getField(), false));
                    if (getMethod == null) {
                        getMethod = ClassUtil.getMethod(record.getClass(), ClassUtil.getGetterMethodName(cell.getField(), true));
                    }
                    if (getMethod != null) {
                        try {
                            value = getMethod.invoke(record);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            value = "";
                        }
                    } else {
                        value = "";
                    }
                }
                //格式化
                value = formatter.apply(value, record, cell, cell.getField(), this.rowCursor, index);
                cell.setValue(value);
                this.buildCell(cell);
                break;
            }
        }
    }

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
        SXSSFTitleCell[][] excelTitles = this.parseCellsJson(titlesJson);
        return setTitles(excelTitles, exportTitles);
    }

    @Override
    public ExcelSheetExport parseTitlesJson(InputStream inputStream, boolean exportTitles) throws IOException, ExcelException {
        SXSSFTitleCell[][] excelTitles = (SXSSFTitleCell[][]) this.parseCellsJson(inputStream);
        return setTitles(excelTitles, exportTitles);
    }

    @Override
    public ExcelSheetExport parseTitlesJson(File file, boolean exportTitles) throws IOException, ExcelException {
        SXSSFTitleCell[][] excelTitles = (SXSSFTitleCell[][]) this.parseCellsJson(file);
        return setTitles(excelTitles, exportTitles);
    }

    @Override
    public ExcelSheetExport setTitles(BaseExcelTitleCell[][] excelTitles, boolean exportTitles) throws ExcelException {
        if (!(excelTitles instanceof SXSSFTitleCell[][])) {
            throw new ExportException("SXSSFExcelSheetExport setTitles excelTitles类型应该为SXSSFTitleCell[][]");
        }
        this.titleCells = handlerExcelTitles(excelTitles);
        this.dataTitleCells = this.searchDataTitleCells(this.titleCells);
        //设置列宽
        for (BaseExcelTitleCell titleCell : this.dataTitleCells) {
            this.setTitleCellColumnWidth(titleCell);
        }
        if (exportTitles) {
            this.parseExportTitles(this.titleCells);
        }
        return this;
    }

    @Override
    public ExcelSheetExport setColumnFields(List<String> fields) throws ExcelException {
        SXSSFTitleCell[][] excelTitles = new SXSSFTitleCell[1][fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            excelTitles[0][i] = new SXSSFTitleCell(fields.get(i));
        }
        return setTitles(excelTitles, false);
    }

    @Override
    public ExcelSheetExport insertPicture(InputStream inputStream, ExcelWorkBook.PictureType pictureType, int dx1, int dy1, int dx2, int dy2, int col1, int row1, int col2, int row2) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            BufferedImage bufferedImage;
            byteArrayOutputStream = new ByteArrayOutputStream();
            bufferedImage = ImageIO.read(inputStream);
            ImageIO.write(bufferedImage, pictureType.suffix, byteArrayOutputStream);

            XSSFDrawing drawing = (XSSFDrawing) this.sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(dx1, dy1, dx2, dy2, (short) col1, row1, (short) col2, row2);
            anchor.setAnchorType(3);

            drawing.createPicture(anchor, this.sxssfWorkbook.addPicture(byteArrayOutputStream.toByteArray(), pictureType.value));
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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