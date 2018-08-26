package pub.avalon.holygrail.excel.bean;

import pub.avalon.holygrail.excel.norm.CellStyle;
import pub.avalon.holygrail.excel.norm.ExcelWorkBookExport;

/**
 * SXSSFWorkbook 单元格
 *
 * @author 白超
 * @date 2018/1/17
 */
public class SXSSFCell extends XSSFCell {

    public SXSSFCell(ExcelWorkBookExport excelWorkBookExport, CellStyle cellStyle, int startRowNum, int startColNum) {
        super(excelWorkBookExport, cellStyle, startRowNum, startColNum);
    }

    public SXSSFCell(ExcelWorkBookExport excelWorkBookExport, CellStyle cellStyle, int startRowNum, int startColNum, int rowSpan, int colSpan) {
        super(excelWorkBookExport, cellStyle, startRowNum, startColNum, rowSpan, colSpan);
    }
}
