package com.avalon.holygrail.excel.bean;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * SXSSF装载器
 * Created by 白超 on 2018/1/18.
 */
public class SXSSFLoader extends XSSFLoader {

    public SXSSFLoader(Workbook workbook) {
        super(workbook);
    }
}
