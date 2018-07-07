package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.Limit;
import com.avalon.holygrail.ss.norm.ResultInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 模型视图
 */
public class ModelView extends LimitDataView {

	public static final String RECORD_KEY = "record";
	public static final String RECORDS_KEY = "records";

	protected Object record;

	protected Map<?, ?> records = new HashMap<>();

	public ModelView(ResultInfo resultInfo) {
		super(resultInfo);
	}

	public ModelView(ResultInfo resultInfo, Collection<?> rows) {
		super(resultInfo, rows);
	}

	public ModelView(ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows) {
		super(resultInfo, total, currPage, pageSize, rows);
	}

	public ModelView(ResultInfo resultInfo, Limit limit, Collection<?> rows) {
		super(resultInfo, limit, rows);
	}

	public ModelView(ResultInfo resultInfo, Map<?, ?> records) {
		super(resultInfo);
		this.records = records;
	}

	public ModelView(ResultInfo resultInfo, Collection<?> rows, Map<?, ?> records) {
		super(resultInfo, rows);
		this.records = records;
	}

	public ModelView(ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows, Map<?, ?> records) {
		super(resultInfo, total, currPage, pageSize, rows);
		this.records = records;
	}

	public ModelView(ResultInfo resultInfo, Limit limit, Collection<?> rows, Map<?, ?> records) {
		super(resultInfo, limit, rows);
		this.records = records;
	}

	public ModelView(ResultInfo resultInfo, Object record) {
		super(resultInfo);
		this.record = record;
	}

	public ModelView(ResultInfo resultInfo, Collection<?> rows, Object record) {
		super(resultInfo, rows);
		this.record = record;
	}

	public ModelView(ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows, Object record) {
		super(resultInfo, total, currPage, pageSize, rows);
		this.record = record;
	}

	public ModelView(ResultInfo resultInfo, Limit limit, Collection<?> rows, Object record) {
		super(resultInfo, limit, rows);
		this.record = record;
	}

	public ModelView(ResultInfo resultInfo, Map<?, ?> records, Object record) {
		super(resultInfo);
		this.records = records;
		this.record = record;
	}

	public ModelView(ResultInfo resultInfo, Collection<?> rows, Map<?, ?> records, Object record) {
		super(resultInfo, rows);
		this.records = records;
		this.record = record;
	}

	public ModelView(ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows, Map<?, ?> records, Object record) {
		super(resultInfo, total, currPage, pageSize, rows);
		this.records = records;
		this.record = record;
	}

	public ModelView(ResultInfo resultInfo, Limit limit, Collection<?> rows, Map<?, ?> records, Object record) {
		super(resultInfo, limit, rows);
		this.records = records;
		this.record = record;
	}

	public ModelView(ResultInfo resultInfo, Map<?, ?> records, Limit limit) {
		super(resultInfo, limit);
		this.records = records;
	}

	public Map<?, ?> getRecords() {
		return records;
	}

	public void setRecords(Map<?, ?> records) {
		this.records = records;
	}

	public Object getRecord() {
		return record;
	}

	public void setRecord(Object record) {
		this.record = record;
	}
}
