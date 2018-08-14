package com.avalon.holygrail.excel.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.avalon.holygrail.excel.bean.XSSFCell;
import com.avalon.holygrail.excel.bean.XSSFTitleCell;
import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.norm.ExcelParser;
import com.avalon.holygrail.excel.norm.MergeCell;

import java.util.ArrayList;
import java.util.List;

/**
 * XSSFWorkbook
 * Created by 白超 on 2018/1/24.
 */
public abstract class XSSFExcelParserAbstract implements ExcelParser {

    /**
     * 解析表头json数据
     *
     * @param titlesJson 表头json
     * @return 表头信息二维数组
     */
    @Override
    public XSSFTitleCell[][] parseCellsJson(String titlesJson) {
        JSONArray jsonArray = JSON.parseArray(titlesJson);
        XSSFTitleCell[][] rs = new XSSFTitleCell[jsonArray.size()][];
        for (int i = 0; i < jsonArray.size(); i++) {
            List<XSSFTitleCell> list = JSON.parseArray(jsonArray.get(i).toString(), XSSFTitleCell.class);
            rs[i] = list.toArray(new XSSFTitleCell[list.size()]);
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
