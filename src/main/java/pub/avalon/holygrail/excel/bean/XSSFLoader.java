package pub.avalon.holygrail.excel.bean;

import com.alibaba.druid.sql.visitor.functions.Char;
import pub.avalon.holygrail.excel.exception.ExcelException;
import pub.avalon.holygrail.excel.model.BaseCell;
import pub.avalon.holygrail.excel.norm.CellStyle;
import pub.avalon.holygrail.excel.norm.Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * XSSF装载器
 *
 * @author 白超
 * @date 2018/1/24
 */
public class XSSFLoader {

    private XSSFLoader() {
    }

    private Workbook workbook;

    public XSSFLoader(Workbook workbook) {
        this.workbook = workbook;
    }

    public void loadCell(BaseCell cellData, Cell poiCell) {
        this.setValue(cellData.getCellValue(), poiCell);
        this.setCellStyle(cellData.getCellStyle(), poiCell);
        this.setFont(cellData.getCellStyle().getFont(), poiCell);
    }


    public Object getValue(Cell poiCell) throws ExcelException {
        switch (poiCell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return poiCell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return poiCell.getNumericCellValue();
            case Cell.CELL_TYPE_BOOLEAN:
                return poiCell.getBooleanCellValue();
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_ERROR:
                return poiCell.getErrorCellValue();
            default:
                throw new ExcelException("暂不支持获取该单元格类型值");
        }
    }

    public void setFont(Font font, Cell poiCell) {
        if (font instanceof FontProxy) {
            poiCell.getCellStyle().setFont(((FontProxy) font).getFont());
            return;
        }
        if (font instanceof Font.DefaultFont) {
            FontProxy fp = new FontProxy(this.workbook.createFont());
            BeanUtils.copyProperties(font, fp);
            poiCell.getCellStyle().setFont(fp.getFont());
            return;
        }
    }

    public void setCellStyle(CellStyle cellStyle, Cell poiCell) {
        if (cellStyle instanceof CellStyleProxy) {
            poiCell.setCellStyle(((CellStyleProxy) cellStyle).getCellStyle());
            return;
        }
        if (cellStyle instanceof CellStyle.DefaultCellStyle) {
            CellStyleProxy csp = new CellStyleProxy(this.workbook.createCellStyle(), new FontProxy(this.workbook.createFont()));
            BeanUtils.copyProperties(cellStyle, csp);
            poiCell.setCellStyle(csp.getCellStyle());
            return;
        }
    }

    public void setValue(Object value, Cell poiCell) {
        if (value == null) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_BLANK);
            return;
        }
        if (value instanceof String) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_STRING);
            poiCell.setCellValue((String) value);
            return;
        }
        if (value instanceof Boolean) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_BOOLEAN);
            poiCell.setCellValue((boolean) value);
            return;
        }
        if (value instanceof Integer) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_NUMERIC);
            poiCell.setCellValue((int) value);
            return;
        }
        if (value instanceof Long) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_NUMERIC);
            poiCell.setCellValue((long) value);
            return;
        }

        if (value instanceof Double) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_NUMERIC);
            poiCell.setCellValue((double) value);
            return;
        }
        if (value instanceof Float) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_NUMERIC);
            poiCell.setCellValue((float) value);
            return;
        }
        if (value instanceof Short) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_NUMERIC);
            poiCell.setCellValue((short) value);
            return;
        }
        if (value instanceof Byte) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_NUMERIC);
            poiCell.setCellValue((byte) value);
            return;
        }
        if (value instanceof Char) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_NUMERIC);
            poiCell.setCellValue((char) value);
            return;
        }
        if (value instanceof BigDecimal) {
            poiCell.setCellType(org.apache.poi.xssf.streaming.SXSSFCell.CELL_TYPE_NUMERIC);
            poiCell.setCellValue(((BigDecimal) value).doubleValue());
            return;
        }
        poiCell.setCellType(SXSSFCell.CELL_TYPE_STRING);
        poiCell.setCellValue((String) value);
        return;
    }

}
