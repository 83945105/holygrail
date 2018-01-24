package com.avalon.holygrail;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.util.Export;
import com.avalon.holygrail.util.Import;

import java.io.File;
import java.io.IOException;

/**
 * Created by 白超 on 2018/1/24.
 */
public class Test {

    public static void main(String[] args) throws IOException, ExcelException {

        Export.buildSXSSFExportExcelWorkBook()
                .createSheet()
                .parseTitlesJson("")
                .importData(null)
                .export("");

        Import.buildXSSFImportExcelWorkBook()
                .parseFile(new File("D://SubScoreRangeManager.xlsx"))
                .getSheet(0).readRows();
    }
}
