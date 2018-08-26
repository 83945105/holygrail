package pub.avalon.holygrail.response.views;

import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 验证结果视图
 *
 * @author 白超
 */
public class ValidateResultView extends MessageView {

    protected boolean success;

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
