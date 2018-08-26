package pub.avalon.holygrail.response.plugins;

import com.alibaba.fastjson.JSONObject;
import pub.avalon.holygrail.response.views.DataView;
import pub.avalon.holygrail.response.views.JsonView;
import pub.avalon.holygrail.utils.StringUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Json转JavaBean
 *
 * @author 白超
 * @date 2018/6/3
 */
public class DataViewDeserializer extends JsonDeserializer<DataView> {

    @Override
    public DataView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String text = node.asText();
        if (StringUtil.isEmpty(text)) {
            text = node.textValue();
        }
        if (StringUtil.isEmpty(text)) {
            text = node.toString();
        }
        if (StringUtil.isEmpty(text)) {
            throw new JsonParseException(jsonParser, "DataView convert to JsonView Fail");
        }
        return JSONObject.parseObject(text, JsonView.class);
    }
}
