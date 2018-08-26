package pub.avalon.holygrail.excel.bean;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * SXSSF装载器
 *
 * @author 白超
 * @date 2018/1/18
 */
public class SXSSFLoader extends XSSFLoader {

    public SXSSFLoader(Workbook workbook) {
        super(workbook);
    }
}
