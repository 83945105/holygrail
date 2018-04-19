package com.avalon.holygrail.ss.bean;

import com.avalon.holygrail.ss.norm.MySqlLimit;

/**
 * <p>Title: 分页工具类</p>
 * <p>Description: </p>
 * @author 白超
 * @date 2016年10月7日 下午10:34:59
 * @版本 V 1.0
 */
public class PageSupport implements MySqlLimit {

	/** 总记录数 */
	private int total;
	/** 总页数 */
	private int totalPageCount = 1;
	/** 当前页号 */
	private int currentPage = 1;
	/** 每页显示条数 */
	private int pageSize = 1;
	/** Oracle起始记录号 */
	private int oracleStartNo;
	/** Oracle结束记录号 */
	private int oracleEndNo;
	/** MySQL起始记录号 */
	private int mySQLStartNo = -1;

	public PageSupport() {}

	public PageSupport(int currentPageNo, int pageSize) {
		this.currentPage = currentPageNo > 0 ? currentPageNo : 1;
		this.pageSize = pageSize > 0 ? pageSize : 1;
	}

	public PageSupport(int total, int currentPageNo, int pageSize) {
		this.total = total > 0 ? total : 0;
		this.currentPage = currentPageNo > 0 ? currentPageNo : 1;
		this.pageSize = pageSize > 0 ? pageSize : 1;
	}

	/** 设置总记录数 */
	public void setTotal(int total) {
		this.total = total > 0 ? total : 0;
	}
	/** 获取总记录数 */
	public int getTotal() {
		return total;
	}

	@Override
	public int getCurrPageNum() {
		return getCurrentPage();
	}

	/** 设置当前页号 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage > 0 ? currentPage : 1;
	}
	/** 获取当前页号 */
	public int getCurrentPage() {
		return currentPage;
	}
	
	/** 设置每页显示条数 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize > 0 ? pageSize : 1;
	}
	/** 获取每页显示条数 */
	public int getPageSize() {
		return this.pageSize;
	}

	@Override
	public int getTotalPage() {
		return getTotalPageCount();
	}

	/** 计算总页数 */
	private void setTotalPageCount() {
		if (total % pageSize == 0) {
			this.totalPageCount = total / pageSize;
		} else if (total % pageSize > 0) {
			this.totalPageCount = total / pageSize + 1;
		} else {
			this.totalPageCount = 0;
		}
	}
	/** 获取总页数 */
	public int getTotalPageCount() {
		setTotalPageCount();
		return totalPageCount > 0 ? totalPageCount : 1;
	}
	
	public void setOracleStartNo(int oracleStartNo) {
		this.oracleStartNo = oracleStartNo > 0 ? oracleStartNo : 1;
	}
	public int getOracleStartNo() {
		return this.oracleStartNo != 0 ? this.oracleStartNo : (this.currentPage - 1) * this.pageSize + 1;
	}

	public void setOracleEndNo(int oracleEndNo) {
		this.oracleEndNo = oracleEndNo > 0 ? oracleEndNo : 1;
	}
	public int getOracleEndNo() {
		return this.oracleEndNo != 0 ? this.oracleEndNo : this.currentPage * this.pageSize;
	}

	public void setMySQLStartNo(int mySQLStartNo) {
		this.mySQLStartNo = mySQLStartNo > 0 ? mySQLStartNo : 0;
	}
	public int getMySQLStartNo() {
		return this.mySQLStartNo != -1 ? this.mySQLStartNo : (this.currentPage - 1) * this.pageSize;
	}

	@Override
	public Integer getLimitStart() {
		return getMySQLStartNo();
	}

	@Override
	public int getLimitEnd() {
		return getPageSize();
	}

}
