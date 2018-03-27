package com.avalon.holygrail.excel.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.avalon.holygrail.excel.bean.XSSFExcelTitle;
import com.avalon.holygrail.excel.bean.XSSFMergeCell;
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

    @Override
    public XSSFMergeCell buildTitleMergeCell(ExcelTitleCellAbstract excelTitle, int startRow, int endRow, int startCol, int endCol) throws ExcelException {
        XSSFMergeCell mergeCell = new XSSFMergeCell(startRow, startCol , endRow - startRow + 1, endCol - startCol + 1);

        excelTitle.copyCellOptionSelective(mergeCell);//设置属性
        excelTitle.setCellStyleByName(mergeCell);//设置样式

        return mergeCell;
    }

    /**
     * 搜寻与数据直接相关的field名称,按照单元格顺序排列,从第1列开始,如果没有就设置为""
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
                fs.add(((XSSFMergeCell)mergeCell).getField());
            }
            start = mergeCell.getEndColNum() + 1;
        }
        return fs;
    }
}
