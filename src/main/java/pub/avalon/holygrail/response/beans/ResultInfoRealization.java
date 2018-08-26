package pub.avalon.holygrail.response.beans;

import java.util.Collection;

/**
 * 系统提示结果集
 *
 * @author 白超
 */
public class ResultInfoRealization implements ResultInfo {

    /**
     * 消息提示类型
     */
    protected ResultCode resultCode;

    /**
     * 提示代码
     */
    protected int messageCode;

    /**
     * 提示信息
     */
    protected String message;

    /**
     * 异常信息
     */
    protected String exceptionMessage;

    /**
     * 提示信息明细列表
     */
    protected Collection<ResultInfo> details;

    public ResultInfoRealization() {
    }

    public ResultInfoRealization(ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public ResultInfoRealization(ResultCode resultCode, String message, String exceptionMessage) {
        this.resultCode = resultCode;
        this.message = message;
        this.exceptionMessage = exceptionMessage;
    }

    public ResultInfoRealization(ResultCode resultCode, int messageCode, String message) {
        this.resultCode = resultCode;
        this.messageCode = messageCode;
        this.message = message;
    }

    public ResultInfoRealization(ResultCode resultCode, Collection<ResultInfo> details) {
        this.resultCode = resultCode;
        this.details = details;
    }

    public ResultInfoRealization(ResultCode resultCode, int messageCode, Collection<ResultInfo> details) {
        this.resultCode = resultCode;
        this.messageCode = messageCode;
        this.details = details;
    }

    @Override
    public boolean isSuccess() {
        return this.resultCode == ResultCode.SUCCESS;
    }

    @Override
    public boolean isFail() {
        return this.resultCode == ResultCode.FAIL;
    }

    @Override
    public boolean isError() {
        return this.resultCode == ResultCode.ERROR;
    }

    //------------------------------------get set---------------------------------------//

    @Override
    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public Collection<ResultInfo> getDetails() {
        return details;
    }

    public void setDetails(Collection<ResultInfo> details) {
        this.details = details;
    }

    @Override
    public int getType() {
        return resultCode.getCode();
    }

    public void setType(ResultCode type) {
        this.resultCode = type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageCode() {
        return messageCode;
    }

    @Override
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    @Override
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}
