package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.model.XSSFExcelParserAbstract;
import com.avalon.holygrail.excel.norm.ExcelSheetImport;
import com.avalon.holygrail.excel.norm.ExcelWorkBookImport;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * XSSFExcelWorkBookImport 导入Excel
 * Created by 白超 on 2018/1/24.
 */
public class XSSFExcelWorkBookImport extends XSSFExcelParserAbstract implements ExcelWorkBookImport {

    protected XSSFWorkbook xssfWorkbook;

    protected ArrayList<XSSFExcelSheetImport> sheets = new ArrayList<>();

    public XSSFExcelWorkBookImport() {
        this.xssfWorkbook = new XSSFWorkbook();
    }

    public XSSFExcelWorkBookImport(XSSFWorkbook xssfWorkbook) {
        this.xssfWorkbook = xssfWorkbook;
    }

    /**
     * 初始化Sheets
     * @return
     */
    protected ExcelWorkBookImport initSheets() {
        for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
            this.sheets.add(new XSSFExcelSheetImport(xssfWorkbook.getSheetAt(i), this));
        }
        return this;
    }

    @Override
    public ExcelWorkBookImport parseFile(InputStream inputStream) throws IOException {
        this.xssfWorkbook = new XSSFWorkbook(inputStream);
        this.initSheets();
        return this;
    }

    @Override
    public ExcelSheetImport getSheet(int index) {
        return this.sheets.get(index);
    }

    @Override
    public int getSheetSize() {
        return this.sheets.size();
    }

}
