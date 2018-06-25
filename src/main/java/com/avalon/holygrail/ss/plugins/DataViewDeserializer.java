package com.avalon.holygrail.ss.plugins;

import com.alibaba.fastjson.JSONObject;
import com.avalon.holygrail.ss.view.DataView;
import com.avalon.holygrail.ss.view.JsonView;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * 用于Jackson反序列化
 * Created by 白超 on 2018/6/3.
 */
public class DataViewDeserializer extends JsonDeserializer<DataView> {

    @Override
    public DataView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        JsonView jsonView = JSONObject.parseObject(node.asText(), JsonView.class);

        return jsonView;
    }
}
