package com.avalon.holygrail.excel.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.avalon.holygrail.excel.bean.XSSFCell;
import com.avalon.holygrail.excel.bean.XSSFExcelTitle;
import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.norm.ExcelParser;
import com.avalon.holygrail.excel.norm.MergeCell;

import java.util.ArrayList;
import java.util.List;

/**
 * XSSFWorkbook
 *
 * @author 白超
 * @date 2018/1/24
 */
public abstract class AbstractXSSFExcelParser implements ExcelParser {

    /**
     * 解析表头json数据
     *
     * @param titlesJson 表头json
     * @return 表头信息二维数组
     */
    @Override
    public XSSFExcelTitle[][] parseCellsJson(String titlesJson) {
        JSONArray jsonArray = JSON.parseArray(titlesJson);
        XSSFExcelTitle[][] rs = new XSSFExcelTitle[jsonArray.size()][];
        for (int i = 0; i < jsonArray.size(); i++) {
            List<XSSFExcelTitle> list = JSON.parseArray(jsonArray.get(i).toString(), XSSFExcelTitle.class);
            rs[i] = list.toArray(new XSSFExcelTitle[list.size()]);
        }
        return rs;
    }

    /**
     * 搜寻与数据直接相关的field名称,按照单元格顺序排列,从第1列开始,如果没有就设置为""
     *
     * @param mergeCellList 单元格合并信息
     * @return 数据Fields
     */
    @Deprecated
    public ArrayList<String> searchDataTitleFields(List<MergeCell> mergeCellList) {
        ArrayList<String> fs = new ArrayList<>();
        int start = 1;
        for (MergeCell mergeCell : mergeCellList) {
            for (int i = start; i < mergeCell.getStartColNum(); i++) {
                fs.add("");
            }
            for (int i = mergeCell.getStartColNum(); i <= mergeCell.getEndColNum(); i++) {
                fs.add(((XSSFCell) mergeCell).getField());
            }
            start = mergeCell.getEndColNum() + 1;
        }
        return fs;
    }

    @Override
    public BaseExcelTitleCell buildExcelTitleCell(BaseExcelTitleCell excelTitle, int startRow, int endRow, int startCol, int endCol) throws ExcelException {
        excelTitle.setStartRowNum(startRow);
        excelTitle.setStartColNum(startCol);
        return excelTitle;
    }

}
