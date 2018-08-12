package com.avalon.holygrail.ss.view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.avalon.holygrail.ss.norm.Json;
import com.avalon.holygrail.ss.norm.ResultInfo;
import com.avalon.holygrail.ss.plugins.DataViewDeserializer;
import com.avalon.holygrail.ss.plugins.DataViewSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.beans.Transient;
import java.io.Serializable;

import static com.avalon.holygrail.ss.view.MessageView.RESULT_INFO_PARAM;

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
