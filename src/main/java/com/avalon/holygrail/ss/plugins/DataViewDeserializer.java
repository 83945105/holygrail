package com.avalon.holygrail.ss.plugins;

import com.alibaba.fastjson.JSONObject;
import com.avalon.holygrail.ss.view.DataView;
import com.avalon.holygrail.ss.view.JsonView;
import com.avalon.holygrail.util.StringUtil;
import com.fasterxml.jackson.core.JsonParseException;
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

        String text = node.asText();
        if(StringUtil.isEmpty(text)) {
            text = node.textValue();
        }
        if(StringUtil.isEmpty(text)) {
            text = node.toString();
        }
        if(StringUtil.isEmpty(text)) {
            throw new JsonParseException(jsonParser, "DataView convert to JsonView Fail");
        }

        JsonView jsonView = JSONObject.parseObject(text, JsonView.class);

        return jsonView;
    }
}