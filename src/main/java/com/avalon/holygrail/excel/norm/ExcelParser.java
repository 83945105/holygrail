package com.avalon.holygrail.excel.norm;

import com.avalon.holygrail.excel.bean.ExcelTitleCellError;
import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.excel.exception.ExcelTitleException;
import com.avalon.holygrail.excel.model.BaseCell;
import com.avalon.holygrail.excel.model.BaseExcelTitleCell;
import com.avalon.holygrail.util.SortUtil;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * Excel解析器
 * Created by 白超 on 2018/1/17.
 */
public interface ExcelParser {

    /**
     * 解析单元格json数据
     *
     * @param titlesJson 表头json
     * @return 表头信息二维数组
     */
    BaseCell[][] parseCellsJson(String titlesJson);

    /**
     * 构建Excel 表头单元格
     *
     * @param excelTitle 表头
     * @param startRow   占用开始行号
     * @param endRow     占用结束行
     * @param startCol   占用开始列
     * @param endCol     占用结束列
     * @return 单元格合并对象
     */
    BaseExcelTitleCell buildExcelTitleCell(BaseExcelTitleCell excelTitle, int startRow, int endRow, int startCol, int endCol) throws ExcelException;

    /**
     * 搜寻影响数据的表头合并单元格数据
     * 因为表头可能有各种方式合并的情况,该方法用于找到最下面一排也就是和数据对应的合并单元格
     *
     * @param titles 表头合并单元格
     * @return 数据表头
     */
    default LinkedList<BaseExcelTitleCell> searchDataTitleCells(List<BaseExcelTitleCell> titles) {
        BaseExcelTitleCell source;
        BaseExcelTitleCell target;
        LinkedList<BaseExcelTitleCell> targetMergeCell = new LinkedList<>();
        //先把所有源放入目标
        targetMergeCell.addAll(titles);
        //遍历源,用源的每一个元素去和目标进行比对位置
        for (int i = 0; i < titles.size(); i++) {
            source = titles.get(i);
            for (int j = 0; j < targetMergeCell.size(); ) {
                target = targetMergeCell.get(j);
                //比对位置
                //如果当前源在目标下面,且二者列有交集,则当前目标不符合条件,要删除
                if (source.getStartRowNum() > target.getEndRowNum() && !(source.getStartColNum() > target.getEndColNum() || source.getEndColNum() < target.getStartColNum())) {
                    //执行删除,删除后后一个元素前移,不用增加j
                    targetMergeCell.remove(j);
                } else {
                    //不删除的时候在进行下一个元素
                    j++;
                }

            }
        }
        //排序
        SortUtil.bubbleSort(targetMergeCell, (left, right) -> left.getStartColNum() > right.getStartColNum());
        return targetMergeCell;
    }

    /**
     * 处理表头
     *
     * @param titles         表头对象二维数组
     * @param defaultSeatRow 记录位置信息初始化默认行数
     * @param defaultSeatCol 记录位置信息初始化默认列数
     * @param handler        处理单元格合并对象回调函数
     * @throws ExcelTitleException
     */
    default void handlerExcelTitles(BaseExcelTitleCell[][] titles, int defaultSeatRow, int defaultSeatCol, Consumer<BaseExcelTitleCell> handler) throws ExcelException {
        BaseExcelTitleCell[] excelTitles;
        BaseExcelTitleCell excelTitle;
        int endRow;//结束行
        int endCol;//结束列
        int[][] seat = new int[defaultSeatRow][defaultSeatCol];//位置
        int[] cursor;//游标
        for (int i = 0; i < titles.length; i++) {
            excelTitles = titles[i];
            for (int j = 0; j < excelTitles.length; j++) {
                cursor = searchStartCursor(seat, i);
                excelTitle = excelTitles[j];
                seat = validateExpandSeat(seat, cursor, excelTitle);
                //将占用的位置设置为已占用
                endRow = cursor[0] + excelTitle.getRowSpan() - 1;
                endCol = cursor[1] + excelTitle.getColSpan() - 1;
                for (int k = cursor[0]; k <= endRow; k++) {
                    for (int l = cursor[1]; l <= endCol; l++) {
                        seat[k][l] = SeatStatus.YES.value;
                    }
                }
                handler.accept(this.buildExcelTitleCell(excelTitle, cursor[0] + 1, endRow, cursor[1] + 1, endCol));
            }
        }
    }

    /**
     * 解析json数据
     *
     * @param inputStream json数据输入流
     * @return 表头信息二维数组
     * @throws IOException
     */
    default BaseCell[][] parseCellsJson(InputStream inputStream) throws IOException {
        InputStreamReader reader = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new InputStreamReader(inputStream, "UTF-8");
            br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return parseCellsJson(sb.toString());
    }

    /**
     * 解析单元格json数据
     *
     * @param file json数据文件
     * @return 表头信息二维数组
     * @throws IOException
     */
    default BaseCell[][] parseCellsJson(File file) throws IOException {
        return this.parseCellsJson(new FileInputStream(file));
    }

    /**
     * 处理表头
     * 初始化位置信息默认行数为titles长度2倍,列数默认为10
     *
     * @param titles 表头对象二维数组
     * @return 单元格合并集合
     * @throws ExcelTitleException
     */
    default ArrayList<BaseExcelTitleCell> handlerExcelTitles(BaseExcelTitleCell[][] titles) throws ExcelException {
        return handlerExcelTitles(titles, titles.length * 2, 10);
    }

    /**
     * 处理表头
     *
     * @param titles         表头对象二维数组
     * @param defaultSeatRow 记录位置信息初始化默认行数
     * @param defaultSeatCol 记录位置信息初始化默认列数
     * @return 单元格合并集合
     * @throws ExcelTitleException
     */
    default ArrayList<BaseExcelTitleCell> handlerExcelTitles(BaseExcelTitleCell[][] titles, int defaultSeatRow, int defaultSeatCol) throws ExcelException {
        ArrayList<BaseExcelTitleCell> rs = new ArrayList<>();
        handlerExcelTitles(titles, defaultSeatRow, defaultSeatCol, mergeCell -> rs.add(mergeCell));
        return rs;
    }

    /**
     * 搜寻位置中指定行第一个没被占用的起点
     *
     * @param seat   位置二维数组
     * @param rowNum 行号
     * @return 起点游标
     */
    default int[] searchStartCursor(int[][] seat, int rowNum) {
        int len = seat[rowNum] == null ? 0 : seat[rowNum].length;
        int i = 0;
        for (; i < len; i++) {
            if (seat[rowNum][i] != SeatStatus.YES.value) {//没有占用
                return new int[]{rowNum, i};
            }
        }
        return new int[]{rowNum, i};
    }

    /**
     * 校验+扩充占用位置
     *
     * @param seat        位置二维数组
     * @param startCursor 起点游标
     * @param excelTitle  表头
     * @return 校验/扩充后的位置
     */
    default int[][] validateExpandSeat(int[][] seat, int[] startCursor, BaseExcelTitleCell excelTitle) throws ExcelTitleException {

        int startRow = startCursor[0];
        int endRow = startRow + excelTitle.getRowSpan() - 1;
        int startCol = startCursor[1];
        int endCol = startCol + excelTitle.getColSpan() - 1;

        for (int i = startRow; i <= endRow; i++) {//循环所占行
            if (seat.length <= i) {
                int[][] copy = seat;//备份
                seat = new int[endRow + 1][];//初始化seat,长度变为结束行号+1
                int j = 0;
                for (; j < copy.length; j++) {
                    seat[j] = copy[j];//复制原始数据
                }
                for (; j <= endRow; j++) {
                    seat[j] = new int[endCol + 1];//扩充行,初始化列数据
                }
                return seat;//扩充了行,不需要继续校验扩充的内容,肯定可用
            }
            for (int j = startCol; j <= endCol; j++) {//循环所占列
                if (seat[i] == null || seat[i].length <= j) {
                    seat[i] = seat[i] == null ? new int[endCol + 1] : Arrays.copyOf(seat[i], endCol + 1);//扩充列
                    break;//扩充了列,不用继续校验当前行的列
                }
                if (seat[i][j] == SeatStatus.YES.value) {//当前单元格存在数据
                    throw new ExcelTitleException(new ExcelTitleCellError(i, j, excelTitle));
                }
            }
        }
        return seat;
    }

    /**
     * 搜寻指定列号对应的表头单元格
     *
     * @param titleCells  表头单元格集合
     * @param columnIndex 列下标
     * @return 对应的合并单元格, 没找到返回null
     */
    default BaseExcelTitleCell searchTitleCell(Collection<BaseExcelTitleCell> titleCells, int columnIndex) {
        int columnNum = columnIndex + 1;
        for (BaseExcelTitleCell titleCell : titleCells) {
            if (columnNum >= titleCell.getStartColNum() && columnNum <= titleCell.getEndColNum()) {
                return titleCell;
            }
        }
        return null;
    }

    /**
     * 位置状态
     */
    enum SeatStatus {

        /**
         * 存在数据
         */
        YES(1),
        /**
         * 不存在数据
         */
        NO(0);

        public int value;

        SeatStatus(int value) {
            this.value = value;
        }

    }
}
