package com.avalon.holygrail.excel.norm;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import java.io.IOException;

/**
 * Excel 导入
 * Created by 白超 on 2018/1/18.
 */
public interface ExcelImport {

    public static void main(String[] args) throws OpenXML4JException, IOException {

        SXSSFCell cell = null;
        XSSFCellStyle cellStyle = null;
        SXSSFSheet sheet = null;
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList();
        DataValidationHelper helper = sheet.getDataValidationHelper();

        String[] options = null;

        DataValidationConstraint constraint = helper.createExplicitListConstraint(options);

        DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);

        if(dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }else {
            dataValidation.setSuppressDropDownArrow(true);
        }

        sheet.addValidationData(dataValidation);
    }
}
