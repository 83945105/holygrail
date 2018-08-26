package pub.avalon.holygrail.excel.factory;

import pub.avalon.holygrail.excel.bean.XSSFExcelWorkBookImport;
import pub.avalon.holygrail.excel.norm.ExcelWorkBookImport;

/**
 * 导入
 *
 * @author 白超
 * @date 2018-1-21
 */
public interface ImportFactory {

    /**
     * 构建基于XSSFWorkBook的Excel导出工作簿
     *
     * @return
     */
    static ExcelWorkBookImport buildXSSFImportExcelWorkBook() {
        return new XSSFExcelWorkBookImport();
    }
}
