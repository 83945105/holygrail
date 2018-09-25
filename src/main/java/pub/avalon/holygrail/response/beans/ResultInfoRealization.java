package pub.avalon.holygrail.response.beans;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 系统提示结果集
 *
 * @author 白超
 */
@SuppressWarnings("unused")
public class ResultInfoRealization implements ResultInfo {

    /**
     * 消息提示类型
     */
    private ResultCode resultCode;

    /**
     * 提示代码
     */
    private int messageCode;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 异常信息
     */
    private String exceptionMessage;

    /**
     * 提示信息明细列表
     */
    private Collection<ResultDetail> resultDetails;

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

    @Override
    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public Collection<ResultDetail> getResultDetails() {
        return resultDetails;
    }

    public ResultInfo addResultDetail(ResultDetail resultDetail) {
        if (this.resultDetails == null) {
            this.resultDetails = new ArrayList<>();
        }
        this.resultDetails.add(resultDetail);
        return this;
    }

    void addAllResultDetails(Collection<ResultDetail> resultDetails) {
        if (this.resultDetails == null) {
            this.resultDetails = new ArrayList<>();
        }
        this.resultDetails.addAll(resultDetails);
    }

    public void setResultDetails(Collection<ResultDetail> resultDetails) {
        this.resultDetails = resultDetails;
    }

}
