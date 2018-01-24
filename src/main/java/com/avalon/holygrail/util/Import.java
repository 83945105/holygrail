package com.avalon.holygrail.util;

import com.avalon.holygrail.excel.bean.XSSFExcelWorkBookImport;
import com.avalon.holygrail.excel.norm.ExcelWorkBookImport;

/**
 * 导入
 * Created by 白超 on 2018-1-21.
 */
public interface Import {

    /**
     * 构建基于XSSFWorkBook的Excel导出工作簿
     */
    static ExcelWorkBookImport buildXSSFImportExcelWorkBook() {
        return new XSSFExcelWorkBookImport();
    }
}
