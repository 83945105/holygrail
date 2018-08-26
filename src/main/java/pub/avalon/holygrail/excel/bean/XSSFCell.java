package pub.avalon.holygrail.excel.bean;

import pub.avalon.holygrail.excel.model.BaseExcelCell;
import pub.avalon.holygrail.excel.norm.CellStyle;
import pub.avalon.holygrail.excel.norm.ExcelWorkBookExport;

/**
 * XSSFWorkbook 单元格
 *
 * @author 白超
 * @date 2018/1/24
 */
public class XSSFCell extends BaseExcelCell {

    public XSSFCell(ExcelWorkBookExport excelWorkBookExport, CellStyle cellStyle, int startRowNum, int startColNum) {
        super(excelWorkBookExport, cellStyle, startRowNum, startColNum);
    }

    public XSSFCell(ExcelWorkBookExport excelWorkBookExport, CellStyle cellStyle, int startRowNum, int startColNum, int rowSpan, int colSpan) {
        super(excelWorkBookExport, cellStyle, startRowNum, startColNum, rowSpan, colSpan);
    }
}
