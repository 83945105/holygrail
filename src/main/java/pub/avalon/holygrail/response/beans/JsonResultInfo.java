package pub.avalon.holygrail.response.beans;

import java.util.LinkedHashMap;

/**
 * @author 白超
 * @date 2018/6/22
 */
public class JsonResultInfo extends LinkedHashMap<String, Object> implements ResultInfo {

    @Override
    public ResultCode getResultCode() {
        String rc = (String) get("resultCode");
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.name().equals(rc)) {
                return resultCode;
            }
        }
        return null;
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

}
