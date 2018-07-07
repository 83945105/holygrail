package com.avalon.holygrail.ss.norm;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.beans.Transient;
import java.io.Serializable;

/**
 * Created by 白超 on 2018/6/22.
 */
public interface Json extends Serializable {

    @Transient
    @JSONField(serialize = false)
    JSONObject getJsonObject();

    /**
     * 转为Json字符串
     *
     * @return
     */
    @Transient
    @JSONField(serialize = false)
    default String toJson() {
        return getJsonObject().toJSONString();
    }

}
