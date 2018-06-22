package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 信息视图
 */
public class MessageView implements DataView {

    /**
     * ResultInfo属性名
     */
    public static final String RESULT_INFO_PARAM = "resultInfo";

    /**
     * 结果集信息
     */
    protected ResultInfo resultInfo;

    public MessageView(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    @Override
    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

}
