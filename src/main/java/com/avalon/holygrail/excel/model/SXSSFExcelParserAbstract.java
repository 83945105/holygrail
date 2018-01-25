package com.avalon.holygrail.excel.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.norm.ExcelParser;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * SXSSFWorkbook 解析器
 * Created by 白超 on 2018/1/16.
 */
public abstract class SXSSFExcelParserAbstract extends XSSFExcelParserAbstract {

    /**
     * 解析表头json数据
     * @param titlesJson 表头json
     * @return 表头信息二维数组
     */
    @Override
    public SXSSFExcelTitle[][] parseCellsJson(String titlesJson) {
        JSONArray jsonArray = JSON.parseArray(titlesJson);
        SXSSFExcelTitle[][] rs = new SXSSFExcelTitle[jsonArray.size()][];
        for (int i = 0; i < jsonArray.size(); i++) {
            List<SXSSFExcelTitle> list = JSON.parseArray(jsonArray.get(i).toString(), SXSSFExcelTitle.class);
            rs[i] = list.toArray(new SXSSFExcelTitle[list.size()]);
        }
        return rs;
    }

    @Override
    public SXSSFMergeCell buildTitleMergeCell(ExcelTitleCellAbstract excelTitle, int startRow, int endRow, int startCol, int endCol) throws ExcelException {
        SXSSFMergeCell mergeCell = new SXSSFMergeCell();

        mergeCell.setCellRangeAddress(new CellRangeAddress(startRow, endRow, startCol, endCol));

        excelTitle.copyCellOptionSelective(mergeCell);//设置属性
        excelTitle.copyCellStyleByName(mergeCell);//设置样式

        return mergeCell;
    }
}
