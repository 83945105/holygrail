package pub.avalon.holygrail.excel.bean;

import pub.avalon.holygrail.excel.norm.ExcelCellHandler;

/**
 * Excel单元格错误信息
 *
 * @author 白超
 * @date 2018/1/16
 */
public class ExcelCellError {

    /**
     * 行号
     */
    private int row;
    /**
     * 列号
     */
    private int col;
    /**
     * 单元格
     */
    private ExcelCellHandler excelCellHandler;

    public ExcelCellError(int row, int col, ExcelCellHandler excelCellHandler) {
        this.row = row;
        this.col = col;
        this.excelCellHandler = excelCellHandler;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public ExcelCellHandler getExcelCellHandler() {
        return excelCellHandler;
    }

    public void setExcelCellHandler(ExcelCellHandler excelCellHandler) {
        this.excelCellHandler = excelCellHandler;
    }
}
