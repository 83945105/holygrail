package com.avalon.holygrail.ss.view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avalon.holygrail.ss.bean.JsonResultInfo;
import com.avalon.holygrail.ss.norm.Limit;
import com.avalon.holygrail.ss.norm.ResultInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Created by 白超 on 2018/6/3.
 */
public class JsonView extends HashMap<String, JSON> implements DataView {

    @Override
    public ResultInfo getResultInfo() {
        JSONObject r = (JSONObject) this.get(MessageView.RESULT_INFO_PARAM);
        JsonResultInfo resultInfo = JSONObject.parseObject(r.toJSONString(), JsonResultInfo.class);
        return resultInfo;
    }

    @SuppressWarnings("unchecked")
    public <T> T getRecord(Class<T> clazz) {
        if (clazz == String.class) {
            return (T) String.valueOf(this.get(ModelView.RECORD_KEY));
        }
        JSONObject jsonObject = (JSONObject) this.get(ModelView.RECORD_KEY);
        if (jsonObject == null) {
            return null;
        }
        return JSONObject.parseObject(jsonObject.toJSONString(), clazz);
    }

    public JSONObject getRecords() {
        return (JSONObject) this.get(ModelView.RECORDS_KEY);
    }

    public <T> ArrayList<T> getRows(Class<T> clazz) {
        ArrayList<T> rows = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) this.get(LimitDataView.ROWS_KEY);
        if (jsonArray == null) {
            return rows;
        }
        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            rows.add(JSONObject.parseObject(((JSONObject) iterator.next()).toJSONString(), clazz));
        }
        return rows;
    }

    public <T> ArrayList<T> getRows(Class<T> clazz, Function<T, T> formatter) {
        ArrayList<T> rows = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) this.get(LimitDataView.ROWS_KEY);
        if (jsonArray == null) {
            return rows;
        }
        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            rows.add(formatter.apply(JSONObject.parseObject(((JSONObject) iterator.next()).toJSONString(), clazz)));
        }
        return rows;
    }

    public <T extends Limit> T getLimit(Class<T> clazz) {
        JSONObject jsonObject = (JSONObject) this.get(PageView.LIMIT_KEY);
        if (jsonObject == null) {
            return null;
        }
        return JSONObject.parseObject(jsonObject.toJSONString(), clazz);
    }
}
