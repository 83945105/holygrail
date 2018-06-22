package com.avalon.holygrail.ss.view;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.avalon.holygrail.ss.bean.JacksonResultInfo;
import com.avalon.holygrail.ss.bean.ResultInfoRealization;
import com.avalon.holygrail.ss.norm.ResultInfo;

import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 白超 on 2018/6/3.
 */
public class JacksonView<K, V> extends HashMap<K, V> implements DataView {

    @Override
    public ResultInfo getResultInfo() {
        JSONObject r = (JSONObject) this.get(MessageView.RESULT_INFO_PARAM);
        JacksonResultInfo resultInfo = JSONObject.parseObject(r.toJSONString(), JacksonResultInfo.class);
        return resultInfo;
    }
}
