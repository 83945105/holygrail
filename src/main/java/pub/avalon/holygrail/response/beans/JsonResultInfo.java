package pub.avalon.holygrail.response.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 白超
 * @date 2018/6/22
 */
public class JsonResultInfo extends LinkedHashMap<String, Object> implements ResultInfo {

    private ResultCode resultCode;

    private Collection<ResultDetail> resultDetails;

    @Override
    public ResultCode getResultCode() {
        if (this.resultCode == null) {
            String rc = (String) get("resultCode");
            for (ResultCode resultCode : ResultCode.values()) {
                if (resultCode.name().equals(rc)) {
                    this.resultCode = resultCode;
                    break;
                }
            }
        }
        return this.resultCode;
    }

    @Override
    public boolean isSuccess() {
        return (boolean) get("success");
    }

    @Override
    public boolean isFail() {
        return (boolean) get("fail");
    }

    @Override
    public boolean isError() {
        return (boolean) get("error");
    }

    @Override
    public int getType() {
        return (int) get("type");
    }

    @Override
    public String getMessage() {
        return (String) get("message");
    }

    @Override
    public int getMessageCode() {
        return (int) get("messageCode");
    }

    @Override
    public String getExceptionMessage() {
        return (String) get("exceptionMessage");
    }

    @Override
    public Collection<ResultDetail> getResultDetails() {
        if (this.resultDetails == null) {
            Collection rds = (Collection) get("resultDetails");
            this.resultDetails = new ArrayList<>(rds.size());
            for (Object rd : rds) {
                if (rd instanceof Map) {
                    JsonResultDetail jsonResultDetail = new JsonResultDetail();
                    for (Map.Entry<?, ?> entry : ((Map<?, ?>) rd).entrySet()) {
                        jsonResultDetail.put((String) entry.getKey(), entry.getValue());
                    }
                    resultDetails.add(jsonResultDetail);
                }
            }
        }
        return this.resultDetails;
    }

}
