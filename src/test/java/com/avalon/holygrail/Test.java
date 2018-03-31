package com.avalon.holygrail;

import com.avalon.holygrail.excel.bean.SXSSFExcelTitle;
import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.norm.CellOption;
import com.avalon.holygrail.excel.norm.CellStyle;
import com.avalon.holygrail.excel.norm.Font;
import com.avalon.holygrail.util.Export;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by 白超 on 2018-2-5.
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException, ExcelException, IOException {

        ArrayList<Map<String, String>> rows = new ArrayList<>();

        Map<String, String> row;

        row = new HashMap<>();
        row.put("id", "123");
        row.put("name", "ABC");
        rows.add(row);

        row = new HashMap<>();
        row.put("id", "123");
        row.put("name", "ABC");
        rows.add(row);

        row = new HashMap<>();
        row.put("id", "123");
        row.put("name", "ABC");
        rows.add(row);

        row = new HashMap<>();
        row.put("id", "123");
        row.put("name", "ABC");
        rows.add(row);

        SXSSFExcelTitle[][] titles = new SXSSFExcelTitle[2][];

        SXSSFExcelTitle[] t1 = new SXSSFExcelTitle[2];

        t1[0] = new SXSSFExcelTitle();
        t1[0].setRowSpan(2);
        t1[0].setWidth(200);

        titles[0] = t1;

        Export.buildSXSSFExportExcelWorkBook()
                .createSheet()
                .setColumnFields("id", "name")
                .importData(rows, (value, record, cellHandler, field, rowCursor, index) -> {

                    cellHandler.setColor(Font.FontColor.RED);
                    cellHandler.setType(CellOption.CellType.COMBOBOX);
                    cellHandler.setOptions(new String[]{"1","2"});

                    cellHandler.setVAlign(CellStyle.V_AlignType.CENTER);
                    cellHandler.setHAlign(CellStyle.H_AlignType.CENTER);

                    return value;
                })
                .export("E://666.xlsx");

    }

}
