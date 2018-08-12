package com.avalon.holygrail.ss.exception;

import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 自定义结果异常
 *
 * @author 白超
 */
public class ResultException extends Exception {

    /**
     * 异常信息
     */
    protected ResultInfo resultInfo;

    /**
     * 跳转的url
     */
    protected String jumpUrl;

    public ResultException(ResultInfo resultInfo) {
        super(resultInfo.getMessage());
        this.resultInfo = resultInfo;
    }

    public ResultException(ResultInfo resultInfo, String jumpUrl) {
        super(resultInfo.getMessage());
        this.resultInfo = resultInfo;
        this.jumpUrl = jumpUrl;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
}
