package com.avalon.holygrail.ss.exception;

import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 自定义结果异常
 */
public class ResultException extends Exception implements ResultInfo {

	protected ResultInfo resultInfo;
	
	public ResultException(ResultInfo resultInfo) {
		super(resultInfo.getMessage());
		this.resultInfo = resultInfo;
	}

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

	@Override
	public boolean isSuccess() {
		return resultInfo.isSuccess();
	}

	@Override
	public boolean isFail() {
		return resultInfo.isFail();
	}

	@Override
	public boolean isError() {
		return resultInfo.isError();
	}

	@Override
	public int getType() {
		return resultInfo.getType();
	}

	@Override
	public String getExceptionMessage() {
		return resultInfo.getExceptionMessage();
	}

	@Override
	public void setExceptionMessage(String exceptionMessage) {
		resultInfo.setExceptionMessage(exceptionMessage);
	}
}
