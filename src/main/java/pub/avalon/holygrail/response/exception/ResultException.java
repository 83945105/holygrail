package pub.avalon.holygrail.response.exception;

import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 自定义结果异常
 *
 * @author 白超
 */
public class ResultException extends RuntimeException {

    /**
     * 异常代号
     */
    protected Integer code;

    /**
     * 异常信息
     */
    protected ResultInfo resultInfo;

    public ResultException(Integer code, ResultInfo resultInfo) {
        super(resultInfo.getMessage());
        this.code = code;
        this.resultInfo = resultInfo;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

}
