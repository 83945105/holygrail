package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 验证结果视图
 */
public class ValidateResultView extends MessageView {

    protected boolean success;

    public ValidateResultView(boolean success) {
        this.success = success;
    }

    public ValidateResultView(ResultInfo resultInfo, boolean success) {
        super(resultInfo);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
