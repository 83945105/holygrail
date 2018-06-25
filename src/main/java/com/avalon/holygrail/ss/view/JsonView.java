package com.avalon.holygrail.ss.view;

import com.alibaba.fastjson.JSONObject;
import com.avalon.holygrail.ss.bean.JsonResultInfo;
import com.avalon.holygrail.ss.norm.ResultInfo;

import java.util.HashMap;

/**
 * Created by 白超 on 2018/6/3.
 */
public class JsonView<K, V> extends HashMap<K, V> implements DataView {

    @Override
    public ResultInfo getResultInfo() {
        JSONObject r = (JSONObject) this.get(MessageView.RESULT_INFO_PARAM);
        JsonResultInfo resultInfo = JSONObject.parseObject(r.toJSONString(), JsonResultInfo.class);
        return resultInfo;
    }
}
