package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.Limit;
import com.avalon.holygrail.ss.norm.ResultInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * EasyUI DataGrid表格视图
 */
public class DataGridView extends LimitDataView {

	/**脚注*/
	private Collection<?> footer = new ArrayList<>();

	public DataGridView(ResultInfo resultInfo) {
		super(resultInfo);
	}

	public DataGridView(ResultInfo resultInfo, Collection<?> rows) {
		super(resultInfo, rows);
	}

	public DataGridView(ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows) {
		super(resultInfo, total, currPage, pageSize, rows);
	}

	public DataGridView(ResultInfo resultInfo, Limit limit, Collection<?> rows) {
		super(resultInfo, limit, rows);
	}

	public DataGridView(ResultInfo resultInfo, Collection<?> rows, Collection<?> footer) {
		super(resultInfo, rows);
		this.footer = footer;
	}

	public DataGridView(ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows, Collection<?> footer) {
		super(resultInfo, total, currPage, pageSize, rows);
		this.footer = footer;
	}

	public DataGridView(ResultInfo resultInfo, Limit limit, Collection<?> rows, Collection<?> footer) {
		super(resultInfo, limit, rows);
		this.footer = footer;
	}

	public Collection<?> getFooter() {
		return footer;
	}
	
	/**
	 * 脚注
	 */
	public void setFooter(Collection<?> footer) {
		this.footer = footer;
	} 
	
}
