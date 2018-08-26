package pub.avalon.holygrail.response.views;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import pub.avalon.holygrail.response.beans.Json;
import pub.avalon.holygrail.response.beans.ResultInfo;
import pub.avalon.holygrail.response.plugins.DataViewDeserializer;
import pub.avalon.holygrail.response.plugins.DataViewSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.beans.Transient;
import java.io.Serializable;

import static pub.avalon.holygrail.response.views.MessageView.RESULT_INFO_PARAM;

/**
 * 数据视图
 * 所有SpringMVC的Controller AJAX返回此接口
 *
 * @author 白超
 */
@JsonSerialize(using = DataViewSerializer.class)
@JsonDeserialize(using = DataViewDeserializer.class)
public interface DataView extends Json, Serializable {

    /**
     * 获取结果集
     *
     * @return
     */
    ResultInfo getResultInfo();

    /**
     * 获取JsonObject对象
     *
     * @return
     */
    @Override
    @Transient
    @JSONField(serialize = false)
    default JSONObject getJsonObject() {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(this);
        jsonObject.put(RESULT_INFO_PARAM, getResultInfo().getJsonObject());
        return jsonObject;
    }

}
