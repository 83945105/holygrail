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

    /**
     * 图片类型
     */
    enum PictureType {

        EMF("emf", 2),
        WMF("wmf", 3),
        PICT("pict", 4),
        JPEG("jpeg", 5),
        PNG("png", 6),
        DIB("dib", 7);

        public String suffix;

        public int value;

        PictureType(String suffix, int value) {
            this.suffix = suffix;
            this.value = value;
        }
    }
}
