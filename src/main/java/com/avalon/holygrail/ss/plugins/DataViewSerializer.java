package com.avalon.holygrail.ss.plugins;

import com.avalon.holygrail.ss.view.DataView;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by 白超 on 2018/6/22.
 */
public class DataViewSerializer extends JsonSerializer<DataView> {

    @Override
    public void serialize(DataView feignView, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(feignView.getJsonObject());
    }

}
