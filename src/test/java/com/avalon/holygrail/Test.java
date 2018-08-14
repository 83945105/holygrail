package com.avalon.holygrail;

import com.avalon.holygrail.bean.EUser;
import com.avalon.holygrail.excel.bean.SXSSFTitleCell;
import com.avalon.holygrail.excel.norm.CellOption;
import com.avalon.holygrail.excel.norm.CellStyle;
import com.avalon.holygrail.excel.norm.Font;
import com.avalon.holygrail.utils.Export;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 白超 on 2018-2-5.
 */
public class Test {

    public static void main(String[] args) throws Exception {

        Test.method4();

    }

    public static void method4() throws Exception {
        String[] methodNames = MethodAccess.get(EUser.class).getMethodNames();
        for (String methodName : methodNames) {
            System.out.println(methodName);
        }
    }

    public static void method3() throws Exception {

        for (Class clazz = EUser.class; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field.getName());
                System.out.println(field.getType().getName());
            }
        }

    }

    public static void method1() throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;

        for (int i = 0; i < 50000; i++) {
            map = new HashMap<>();
            map.put("A", i + "A");
            map.put("B", i + "B");
            map.put("C", i + "C");
            map.put("D", i + "D");
            map.put("E", i + "E");
            map.put("F", i + "F");
            map.put("G", i + "G");
            map.put("H", i + "H");
            map.put("I", i + "I");
            map.put("J", i + "J");
            map.put("K", i + "K");
            map.put("L", i + "L");
            map.put("M", i + "M");
            map.put("N", i + "N");
            list.add(map);
        }

        Export.buildSXSSFExportExcelWorkBook()
                .createCellStyle(0, cellStyle -> {
                    cellStyle.setHorizontalAlignType(CellStyle.HorizontalAlignType.CENTER);
                    cellStyle.setVerticalAlignType(CellStyle.VerticalAlignType.CENTER);
                })
                .createFont(0, font -> {
                    font.setColor(Font.FontColor.BLUE);
                })
                .createSheet()
                .parseTitlesJson(Test.class.getResourceAsStream("/com/avalon/holygrail/excel/bean/666.js"))
                .importData(list, (value, record, cellHandler, field, rowCursor, index) -> {
                    cellHandler.setCellStyle(cellHandler.findCellStyle(0));
                    cellHandler.getCellStyle().setFont(cellHandler.findFont(0));

                    return value;
                })
                .export("E://666.xlsx");
    }

    public static void method2() throws Exception {
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

        SXSSFTitleCell[][] titles = new SXSSFTitleCell[2][];

        SXSSFTitleCell[] t1 = new SXSSFTitleCell[2];

        t1[0] = new SXSSFTitleCell();
        t1[0].setRowSpan(2);
        t1[0].setWidth(200);

        titles[0] = t1;

        Export.buildSXSSFExportExcelWorkBook()
                .createSheet()
                .setColumnFields("id", "name")
                .importData(rows, (value, record, cellHandler, field, rowCursor, index) -> {

                    cellHandler.setCellType(CellOption.CellType.COMBOBOX);
                    cellHandler.setCellOptions(new String[]{"1", "2"});


                    return value;
                })
                .export("E://666.xlsx");
    }

}
