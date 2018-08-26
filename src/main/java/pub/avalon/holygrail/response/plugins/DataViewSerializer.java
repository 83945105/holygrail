package pub.avalon.holygrail.response.plugins;

import pub.avalon.holygrail.response.views.DataView;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * JavaBean转Json
 *
 * @author 白超
 * @date 2018/6/22
 */
public class DataViewSerializer extends JsonSerializer<DataView> {

    @Override
    public void serialize(DataView feignView, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(feignView.getJsonObject());
    }

}
