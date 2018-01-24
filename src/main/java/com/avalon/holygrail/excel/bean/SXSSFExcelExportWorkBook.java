package com.avalon.holygrail.excel.bean;

import com.avalon.holygrail.excel.model.ExcelTitleAbstract;
import com.avalon.holygrail.excel.model.SXSSFExcelExportAbstract;
import com.avalon.holygrail.excel.model.SXSSFMergeCell;
import com.avalon.holygrail.excel.norm.ExcelSheetExport;
import com.avalon.holygrail.excel.norm.ExcelExportWorkBook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

/**
 * SXSSFWorkbook 导出Excel
 * Created by 白超 on 2018/1/17.
 */
public class SXSSFExcelExportWorkBook extends SXSSFExcelExportAbstract implements ExcelExportWorkBook {

    protected SXSSFWorkbook sxssfWorkbook;

    protected ArrayList<SXSSFExcelExportSheet> sheets = new ArrayList<>();

    public SXSSFExcelExportWorkBook() {
        this.sxssfWorkbook = new SXSSFWorkbook();
    }

    public SXSSFExcelExportWorkBook(int rowAccessWindowSize) {
        this.sxssfWorkbook = new SXSSFWorkbook(rowAccessWindowSize);
    }

    public SXSSFExcelExportWorkBook(SXSSFWorkbook workbook) {
        this.sxssfWorkbook = workbook;
    }

    @Override
    public ExcelSheetExport createSheet() {
        return createSheet("sheet" + sheets.size());
    }

    @Override
    public ExcelSheetExport createSheet(String sheetName) {
        SXSSFExcelExportSheet sheet = new SXSSFExcelExportSheet(this.sxssfWorkbook, sheetName, this);
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

    @Override
    public SXSSFMergeCell buildTitleMergeCell(ExcelTitleAbstract excelTitle, int startRow, int endRow, int startCol, int endCol) {
        SXSSFMergeCell mergeCell = new SXSSFMergeCell();

        mergeCell.setCellRangeAddress(new CellRangeAddress(startRow, endRow, startCol, endCol));

        excelTitle.copyCellOption(mergeCell);//设置属性
        excelTitle.copyCellStyle(mergeCell);//设置样式

        return mergeCell;
    }

}
