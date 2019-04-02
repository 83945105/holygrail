package pub.avalon.holygrail;

import pub.avalon.holygrail.response.beans.User;
import pub.avalon.holygrail.excel.exception.ExcelException;
import pub.avalon.holygrail.excel.factory.ImportFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Created by 白超 on 2018/8/14.
 */
public class ImportTest {

    @Test
    void test01() throws IOException, ExcelException, InstantiationException, IllegalAccessException {
        ImportFactory.buildXSSFImportExcelWorkBook()
                .parseFile("E://test.xlsx")
                .getSheet(0)
                .setColumnFields("id", "name", "alias")
                .readRows(User.class, (record, records, rowNum, index) -> {
                    System.out.println(record);
                });
    }

}
