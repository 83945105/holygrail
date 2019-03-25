package pub.avalon.holygrail.response.beans;

import pub.avalon.holygrail.utils.JsonUtil;

import java.util.Collection;

/**
 * @author 白超
 * @date 2019/3/25
 */
public class JsonResultInfo extends AbstractJsonResultInfo {

    private ResultInfo resultInfo;

    public JsonResultInfo(String json) {
        if(json == null) {
            throw new RuntimeException("JsonResultInfo Constructor arg json is null.");
        }
        this.resultInfo = new JacksonResultInfo(JsonUtil.readTree(json));
    }

    @Override
    public ResultCode getResultCode() {
        return this.resultInfo.getResultCode();
    }

    @Override
    public boolean isSuccess() {
        return this.resultInfo.isSuccess();
    }

    @Override
    public boolean isFail() {
        return this.resultInfo.isFail();
    }

    @Override
    public boolean isError() {
        return this.resultInfo.isError();
    }

    @Override
    public int getType() {
        return this.resultInfo.getType();
    }

    @Override
    public String getMessage() {
        return this.resultInfo.getMessage();
    }

    @Override
    public int getMessageCode() {
        return this.resultInfo.getMessageCode();
    }

    @Override
    public String getExceptionMessage() {
        return this.resultInfo.getExceptionMessage();
    }

    @Override
    public Collection<ResultDetail> getResultDetails() {
        return this.resultInfo.getResultDetails();
    }
}
