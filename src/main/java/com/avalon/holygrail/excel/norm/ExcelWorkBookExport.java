package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.bean.SXSSFExcelWorkBookExport;
import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.exception.ExportException;

import java.io.*;
import java.util.function.Consumer;

/**
 * Excel导出工作簿
 *
 * @author 白超
 * @date 2018/1/17
 */
public interface ExcelWorkBookExport extends ExcelWorkBook {

    int MAX_BATCH_CREATE_SHEETS_NUMBER = 100;

    /**
     * 创建工作表
     *
     * @return 工作表对象
     * @throws ExportException
     */
    SheetExportHandler createSheet() throws ExportException;

    /**
     * 创建工作表
     *
     * @param sheetName 工作表表名
     * @return 工作表对象
     * @throws ExportException
     */
    SheetExportHandler createSheet(String sheetName) throws ExportException;

    /**
     * 获取工作表
     *
     * @param index 下标
     * @return 工作表对象
     */
    @Override
    SheetExportHandler getSheet(int index);

    /**
     * 获取所有表格的数据总数
     *
     * @return 所有表格已经导入的数据总数
     */
    default int getTotalSheetDataSize() {
        int rs = 0;
        int ts = getSheetSize();
        for (int i = 0; i < ts; i++) {
            rs += getSheet(i).getTotalDataSize();
        }
        return rs;
    }

    @FunctionalInterface
    interface FormatterSheetName {
        /**
         * 格式化Sheet名
         *
         * @param sheetIndex 当前Sheet在WorkBook中的下标
         * @param index      当前Sheet下标
         * @return 你想要设置的Sheet名称
         */
        String apply(int sheetIndex, int index);
    }

    @FunctionalInterface
    interface HandlerSheetA {

        /**
         * 处理Sheet
         *
         * @param sheet                 待处理的Sheet
         * @param sheetIndex            当前Sheet在WorkBook中的下标
         * @param index                 当前创建的Sheet下标
         * @param totalAllSheetDataSize WorkBook中所有Sheet已经导入的数据总数
         * @param totalSheetDataSize    本次创建的若干Sheet已经导入的数据总数
         * @throws ExcelException
         * @throws IOException
         */
        void accept(SheetExportHandler sheet, int sheetIndex, int index, int totalAllSheetDataSize, int totalSheetDataSize) throws ExcelException, IOException;
    }

    @FunctionalInterface
    interface HandlerSheetB {

        /**
         * 处理Sheet
         *
         * @param sheet                 待处理的Sheet
         * @param sheetIndex            当前Sheet在WorkBook中的下标
         * @param index                 当前创建的Sheet下标
         * @param totalAllSheetDataSize WorkBook中所有Sheet已经导入的数据总数
         * @param totalSheetDataSize    本次创建的若干Sheet已经导入的数据中暑
         * @return 是否继续创建
         * @throws IOException
         * @throws ExcelException
         */
        boolean accept(SheetExportHandler sheet, int sheetIndex, int index, int totalAllSheetDataSize, int totalSheetDataSize) throws IOException, ExcelException;
    }

    /**
     * 批量创建Sheet
     *
     * @param totalSheet         你要创建的Sheet总数(最大支持100)
     * @param formatterSheetName 格式化Sheet名称,需要返回你要创建的Sheet名称
     * @param handlerSheet       处理Sheet
     * @return 当前工作簿对象
     * @throws ExcelException
     * @throws IOException
     */
    default ExcelWorkBookExport createSheets(int totalSheet, SXSSFExcelWorkBookExport.FormatterSheetName formatterSheetName, SXSSFExcelWorkBookExport.HandlerSheetA handlerSheet) throws ExcelException, IOException {
        createSheets(formatterSheetName, (sheet, sheetIndex, index, totalAllSheetDataSize, totalSheetDataSize) -> {
            handlerSheet.accept(sheet, sheetIndex, index, totalAllSheetDataSize, totalSheetDataSize);
            if (index < totalSheet - 1) {
                return true;
            }
            return false;
        });
        return this;
    }

    /**
     * 批量创建Sheet
     *
     * @param totalSheet   你要创建的Sheet总数(最大支持100)
     * @param handlerSheet 处理Sheet
     * @return 当前工作簿对象
     * @throws ExcelException
     * @throws IOException
     */
    default ExcelWorkBookExport createSheets(int totalSheet, SXSSFExcelWorkBookExport.HandlerSheetA handlerSheet) throws ExcelException, IOException {
        createSheets(totalSheet, (sheetIndex, index) -> "sheet" + sheetIndex, handlerSheet);
        return this;
    }

    /**
     * 批量创建Sheet
     *
     * @param formatterSheetName 格式化Sheet名称,需要返回你要创建的Sheet名称
     * @param handlerSheet       处理Sheet,需要返回是否继续创建,最多创建100个Sheet
     * @return 当前工作簿对象
     * @throws IOException
     * @throws ExcelException
     */
    default ExcelWorkBookExport createSheets(SXSSFExcelWorkBookExport.FormatterSheetName formatterSheetName, SXSSFExcelWorkBookExport.HandlerSheetB handlerSheet) throws IOException, ExcelException {
        int totalAllSheetDataSize = getTotalSheetDataSize();
        int totalSheetDataSize = 0;
        boolean goon;
        for (int i = 0; i < MAX_BATCH_CREATE_SHEETS_NUMBER; i++) {
            int sheetIndex = getSheetSize();
            SheetExportHandler s = createSheet(formatterSheetName.apply(sheetIndex, i));
            if (i > 0) {
                totalAllSheetDataSize += getSheet(i - 1).getTotalDataSize();
                totalSheetDataSize += getSheet(i - 1).getTotalDataSize();
            }
            goon = handlerSheet.accept(s, sheetIndex, i, totalAllSheetDataSize, totalSheetDataSize);
            if (!goon) {
                break;
            }
        }
        return this;
    }

    /**
     * 批量创建Sheet
     *
     * @param handlerSheet 处理Sheet,需要返回是否继续创建,最多创建100个Sheet
     * @return 当前工作簿对象
     * @throws IOException
     * @throws ExcelException
     */
    default ExcelWorkBookExport createSheets(SXSSFExcelWorkBookExport.HandlerSheetB handlerSheet) throws IOException, ExcelException {
        createSheets((sheetIndex, index) -> "sheet" + sheetIndex, handlerSheet);
        return this;
    }

    /**
     * 获取单元格样式
     *
     * @param index 已经创建的样式下标
     * @return 单元格样式
     */
    CellStyle findCellStyle(int index);

    /**
     * 获取字体
     *
     * @param index 已经创建的字体下标
     * @return Font
     */
    Font findFont(int index);

    /**
     * 创建单元格样式对象
     *
     * @param index
     * @return 单元格样式对象
     */
    CellStyle createCellStyle(int index);

    /**
     * 创建字体对象
     *
     * @param index
     * @return 字体对象
     */
    Font createFont(int index);

    /**
     * 创建样式对象
     *
     * @param index
     * @param handler
     * @return
     */
    default ExcelWorkBookExport createCellStyle(int index, Consumer<CellStyle> handler) {
        handler.accept(this.createCellStyle(index));
        return this;
    }

    /**
     * 创建字体
     *
     * @param index
     * @param handler
     * @return
     */
    default ExcelWorkBookExport createFont(int index, Consumer<Font> handler) {
        handler.accept(this.createFont(index));
        return this;
    }

    /**
     * 导出Excel
     *
     * @param outFile 目标文件
     * @return 当前对象
     * @throws IOException
     */
    void export(File outFile) throws IOException;

    /**
     * 导出Excel
     *
     * @param outPath 导出地址
     * @return 当前对象
     * @throws IOException
     */
    default void export(String outPath) throws IOException {
        File file = new File(outPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } else if (!file.exists()) {
            file.createNewFile();
        }
        export(new File(outPath));
    }

    /**
     * js模板文件路径
     */
    String TEMPLATE_JAVASCRIPT_FILE_PATH = "/com/avalon/holygrail/excel/model/ExcelTitle.js";

    /**
     * 导出JavaScript模板文件
     *
     * @param path 路径
     * @throws IOException
     */
    static void exportTemplateJavaScriptFile(String path) throws IOException {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        String str;

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            is = ExcelWorkBookExport.class.getResourceAsStream(TEMPLATE_JAVASCRIPT_FILE_PATH);
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);

            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
            while ((str = br.readLine()) != null) {
                bw.write(str);
                bw.newLine();
            }

            System.out.println("成功创建模板文件,文件路径:" + file.getPath());

        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
