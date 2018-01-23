package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 信息视图
 */
public class MessageView implements DataView {
	
	/**结果集信息*/
	protected ResultInfo resultInfo;
	
	public MessageView() {}
	
	public MessageView(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
}
