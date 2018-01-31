package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.ResultInfo;

import java.util.Map;

/**
 * 异常视图
 */
public class ExceptionView extends ModelView {

	/**
	 * 跳转的url
	 */
	protected String jumpUrl;

	public ExceptionView(ResultInfo resultInfo) {
		super(resultInfo);
	}

	public ExceptionView(ResultInfo resultInfo, Map<?, ?> records) {
		super(resultInfo);
		this.records = records;
	}

	public ExceptionView(ResultInfo resultInfo, String jumpUrl) {
		super(resultInfo);
		this.jumpUrl = jumpUrl;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
}
