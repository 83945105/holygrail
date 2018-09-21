package pub.avalon.holygrail.response.plugins;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import pub.avalon.holygrail.response.views.DataView;
import pub.avalon.holygrail.response.views.JsonView;
import pub.avalon.holygrail.utils.JsonUtil;
import pub.avalon.holygrail.utils.StringUtil;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * DataView转JsonView
 *
 * @author 白超
 * @date 2018/6/3
 */
public class DataViewDeserializer extends JsonDeserializer<DataView> {

    @Override
    public DataView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String json = node.toString();
        if (StringUtil.isEmpty(json)) {
            json = node.textValue();
        }
        if (StringUtil.isEmpty(json)) {
            json = node.asText();
        }
        if (StringUtil.isEmpty(json)) {
            throw new JsonParseException(jsonParser, "not find json in jsonParser.");
        }
        JsonView jsonView = new JsonView();
        LinkedHashMap<String, Object> viewData = JsonUtil.parseObject(json, new TypeReference<LinkedHashMap<String, Object>>() {
        });
        if (viewData == null) {
            return jsonView;
        }
        jsonView.putAll(viewData);
        return jsonView;
    }
}
