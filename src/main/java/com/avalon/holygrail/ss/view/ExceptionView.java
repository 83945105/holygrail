package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.ResultInfo;

import java.util.Map;

/**
 * 异常视图
 */
public class ExceptionView extends ModelView {

	public ExceptionView(ResultInfo resultInfo) {
		super(resultInfo);
	}

	public ExceptionView(ResultInfo resultInfo, Map<?, ?> records) {
		super(resultInfo);
		this.records = records;
	}
}
