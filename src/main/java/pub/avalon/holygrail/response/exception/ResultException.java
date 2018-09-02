package pub.avalon.holygrail.response.exception;

import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 自定义结果异常
 *
 * @author 白超
 */
public class ResultException extends RuntimeException {

    /**
     * 异常信息
     */
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

}
