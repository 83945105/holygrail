package pub.avalon.holygrail.excel.bean;

/**
 * @author 白超
 * @date 2018/4/10
 */
public class SXSSFTitleCell extends XSSFTitleCell {

    public SXSSFTitleCell() {
    }

    public SXSSFTitleCell(String field) {
        super(field);
    }

    public SXSSFTitleCell(String title, String field) {
        super(title, field);
    }

    public SXSSFTitleCell(int startRowNum, int startColNum) {
        super(startRowNum, startColNum);
    }

    public SXSSFTitleCell(int startRowNum, int startColNum, int rowSpan, int colSpan) {
        super(startRowNum, startColNum, rowSpan, colSpan);
    }
}
