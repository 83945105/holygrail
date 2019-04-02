package pub.avalon.holygrail.response.beans;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import pub.avalon.holygrail.response.views.DataView;
import pub.avalon.holygrail.response.views.JacksonView;

import java.io.IOException;

/**
 * DataView转JsonView
 *
 * @author 白超
 * @date 2018/6/3
 */
public class DataViewDeserializer extends JsonDeserializer<DataView> {

    @Override
    public DataView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return new JacksonView(jsonParser.readValueAsTree());
    }

}
