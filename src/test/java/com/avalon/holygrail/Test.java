package com.avalon.holygrail;

import com.avalon.holygrail.excel.norm.ExcelSheetImport;
import com.avalon.holygrail.util.Import;

import java.io.File;
import java.io.IOException;

/**
 * Created by 白超 on 2018/1/24.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        ExcelSheetImport sheet = Import.buildXSSFImportExcelWorkBook()
                .parseFile(new File("D://StuManager.xlsx"))
                .getSheet(0);
        System.out.println(sheet.getPhysicalNumberOfRows());
    }
}
