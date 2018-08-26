package pub.avalon.holygrail.response.beans;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.beans.Transient;
import java.io.Serializable;

/**
 * 结果集信息
 *
 * @author 白超
 */
public interface ResultInfo extends Json, Serializable {

    /**
     * 获取结果值
     *
     * @return
     */
    ResultCode getResultCode();

    /**
     * 是否成功
     *
     * @return
     */
    boolean isSuccess();

    /**
     * 是否失败
     *
     * @return
     */
    boolean isFail();

    /**
     * 是否错误
     *
     * @return
     */
    boolean isError();

    /**
     * 结果集类型
     *
     * @return
     */
    int getType();

    /**
     * 获取信息提示
     *
     * @return
     */
    String getMessage();

    /**
     * 获取异常信息
     *
     * @return
     */
    String getExceptionMessage();

    /**
     * 设置异常信息
     *
     * @param exceptionMessage
     */
    void setExceptionMessage(String exceptionMessage);

    /**
     * 获取 JSONObject对象
     *
     * @return JSONObject
     */
    @Override
    @Transient
    @JSONField(serialize = false)
    default JSONObject getJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", getResultCode());
        jsonObject.put("success", isSuccess());
        jsonObject.put("fail", isFail());
        jsonObject.put("error", isError());
        jsonObject.put("type", getType());
        jsonObject.put("message", getMessage());
        jsonObject.put("exceptionMessage", getExceptionMessage());
        return jsonObject;
    }

}
