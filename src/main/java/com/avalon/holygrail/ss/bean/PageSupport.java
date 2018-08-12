package com.avalon.holygrail.ss.bean;

import com.avalon.holygrail.ss.norm.MySqlLimit;

/**
 * 分页工具类
 *
 * @author 白超
 */
public class PageSupport implements MySqlLimit {

    /**
     * 总记录数
     */
    private int total;
    /**
     * 当前页号
     */
    private int currentPage = 1;
    /**
     * 每页显示条数
     */
    private int pageSize = 1;
    /**
     * Oracle起始记录号
     */
    private int oracleStartNo;
    /**
     * Oracle结束记录号
     */
    private int oracleEndNo;
    /**
     * MySQL起始记录号
     */
    private int mySqlStartNo = -1;

    public PageSupport() {
    }

    public PageSupport(int currentPage, int pageSize) {
        this.setCurrentPage(currentPage);
        this.setPageSize(pageSize);
    }

    public PageSupport(int total, int currentPage, int pageSize) {
        this.setTotal(total);
        this.setCurrentPage(currentPage);
        this.setPageSize(pageSize);
    }

    @Override
    public int getTotal() {
        return this.total;
    }

    @Override
    public int getCurrentPage() {
        return this.currentPage;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public int getPageCount() {
        if (this.total % this.pageSize == 0) {
            return this.total / this.pageSize;
        } else if (total % pageSize > 0) {
            return this.total / this.pageSize + 1;
        } else {
            return 1;
        }
    }

    /**
     * 设置总记录数
     */
    public void setTotal(int total) {
        this.total = total > 0 ? total : 0;
    }

    /**
     * 设置当前页号
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage > 0 ? currentPage : 1;
    }

    /**
     * 设置每页显示条数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize > 0 ? pageSize : 1;
    }

    public int getOracleStartNo() {
        return this.oracleStartNo != 0 ? this.oracleStartNo : (this.currentPage - 1) * this.pageSize + 1;
    }

    public void setOracleStartNo(int oracleStartNo) {
        this.oracleStartNo = oracleStartNo > 0 ? oracleStartNo : 1;
    }

    public int getOracleEndNo() {
        return this.oracleEndNo != 0 ? this.oracleEndNo : this.currentPage * this.pageSize;
    }

    public void setOracleEndNo(int oracleEndNo) {
        this.oracleEndNo = oracleEndNo > 0 ? oracleEndNo : 1;
    }

    public int getMySqlStartNo() {
        return this.mySqlStartNo != -1 ? this.mySqlStartNo : (this.currentPage - 1) * this.pageSize;
    }

    public void setMySqlStartNo(int mySqlStartNo) {
        this.mySqlStartNo = mySqlStartNo;
    }

    public void setMySQLStartNo(int mySQLStartNo) {
        this.mySqlStartNo = mySQLStartNo > 0 ? mySQLStartNo : 0;
    }

    @Override
    public Integer getLimitStart() {
        return this.getMySqlStartNo();
    }

    @Override
    public int getLimitEnd() {
        return this.getPageSize();
    }

}
