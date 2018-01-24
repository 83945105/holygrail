package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.model.XSSFExcelImportAbstract;
import com.avalon.holygrail.excel.norm.ExcelImportWorkBook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * XSSFExcelImportWorkBook 导入Excel
 * Created by 白超 on 2018/1/24.
 */
public class XSSFExcelImportWorkBook extends XSSFExcelImportAbstract implements ExcelImportWorkBook {

    protected XSSFWorkbook xssfWorkbook;
}
