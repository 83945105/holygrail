package com.avalon.holygrail.excel.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.avalon.holygrail.excel.bean.SXSSFExcelTitle;

import java.util.List;

/**
 * SXSSFWorkbook 解析器
 * Created by 白超 on 2018/1/16.
 */
public abstract class SXSSFExcelParserAbstract extends XSSFExcelParserAbstract {

    /**
     * 解析单元格json数据
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

}
