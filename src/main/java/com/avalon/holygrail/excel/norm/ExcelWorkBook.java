package com.avalon.holygrail.excel.norm;

/**
 * Excel工作簿
 * Created by 白超 on 2018/1/24.
 */
public interface ExcelWorkBook {

    /**
     * 获取总Sheet数
     * @return 存在的Sheet数量
     */
    int getSheetSize();

    /**
     * 获取工作表
     * @param index 下标
     * @return 工作表对象
     */
    Sheet getSheet(int index);
}
