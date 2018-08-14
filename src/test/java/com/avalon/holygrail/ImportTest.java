package com.avalon.holygrail;

import com.avalon.holygrail.bean.User;
import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.utils.Import;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Created by 白超 on 2018/8/14.
 */
public class ImportTest {

    @Test
    void test01() throws IOException, ExcelException, InstantiationException, IllegalAccessException {
        Import.buildXSSFImportExcelWorkBook()
                .parseFile("E://test.xlsx")
                .getSheet(0)
                .setColumnFields("id", "name", "alias")
                .readRows(User.class, (record, records, rowNum, index) -> {
                    System.out.println(record);
                });
    }

}
