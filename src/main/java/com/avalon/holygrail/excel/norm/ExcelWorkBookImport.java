package com.avalon.holygrail.excel.norm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Excel导入工作簿
 * Created by 白超 on 2018/1/24.
 */
public interface ExcelWorkBookImport extends ExcelWorkBook {

    /**
     * 解析文件
     * @param inputStream 输入流
     * @return
     * @throws IOException
     */
    ExcelWorkBookImport parseFile(InputStream inputStream) throws IOException;

    @Override
    ExcelSheetImport getSheet(int index);

    /**
     * 解析文件
     * @param file 文件
     * @return
     * @throws IOException
     */
    default ExcelWorkBookImport parseFile(File file) throws IOException {
        return this.parseFile(new FileInputStream(file));
    }

    /**
     * 解析文件
     * @param path 文件路径
     * @return
     * @throws IOException
     */
    default ExcelWorkBookImport parseFile(String path) throws IOException {
        return this.parseFile(new File(path));
    }
}
