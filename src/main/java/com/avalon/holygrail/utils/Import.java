package com.avalon.holygrail.utils;

import com.avalon.holygrail.excel.bean.XSSFExcelWorkBookImport;
import com.avalon.holygrail.excel.norm.ExcelWorkBookImport;

/**
 * 导入
 *
 * @author 白超
 * @date 2018-1-21
 */
public interface Import {

    /**
     * 构建基于XSSFWorkBook的Excel导出工作簿
     *
     * @return
     */
    static ExcelWorkBookImport buildXSSFImportExcelWorkBook() {
        return new XSSFExcelWorkBookImport();
    }
}
