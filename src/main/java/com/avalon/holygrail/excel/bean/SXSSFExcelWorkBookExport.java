package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.model.SXSSFExcelParserAbstract;
import com.avalon.holygrail.excel.norm.ExcelSheetExport;
import com.avalon.holygrail.excel.norm.ExcelWorkBookExport;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

/**
 * SXSSFWorkbook 导出Excel
 * Created by 白超 on 2018/1/17.
 */
public class SXSSFExcelWorkBookExport extends SXSSFExcelParserAbstract implements ExcelWorkBookExport {

    protected SXSSFWorkbook sxssfWorkbook;

    protected ArrayList<SXSSFExcelSheetExport> sheets = new ArrayList<>();

    public SXSSFExcelWorkBookExport() {
        this.sxssfWorkbook = new SXSSFWorkbook();
    }

    public SXSSFExcelWorkBookExport(int rowAccessWindowSize) {
        this.sxssfWorkbook = new SXSSFWorkbook(rowAccessWindowSize);
    }

    public SXSSFExcelWorkBookExport(SXSSFWorkbook workbook) {
        this.sxssfWorkbook = workbook;
    }

    @Override
    public ExcelSheetExport createSheet() {
        return createSheet("sheet" + sheets.size());
    }

    @Override
    public ExcelSheetExport createSheet(String sheetName) {
        SXSSFExcelSheetExport sheet = new SXSSFExcelSheetExport(this.sxssfWorkbook, sheetName, this);
        this.sheets.add(sheet);
        return sheet;
    }

    @Override
    public ExcelSheetExport getSheet(int index) {
        return this.sheets.get(index);
    }

    @Override
    public int getSheetSize() {
        return sheets.size();
    }

    @Override
    public void export(File outFile) throws IOException {
        FileOutputStream fos = null;
        OutputStream osw = null;
        try {
            fos = new FileOutputStream(outFile);
            osw = new BufferedOutputStream(fos);
            this.sxssfWorkbook.write(osw);
        } finally {
            try {
                if (osw != null) osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
