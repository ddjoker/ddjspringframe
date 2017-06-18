package com.ddjoker.ddjframe.application.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

/**
 * Created by dong on 2017/6/18.
 */
@JsonComponent
public class BaseModel {
  public static class Serializer extends JsonSerializer<BaseModel> {

    @Override
    public void serialize(BaseModel baseModel, JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

    }
    // ...
  }

  public static class Deserializer extends JsonDeserializer<BaseModel> {

    @Override
    public BaseModel deserialize(JsonParser jsonParser,
        DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {
      return null;
    }
    // ...
  }
}
