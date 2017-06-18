package com.ddjoker.ddjframe.application.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.jackson.JsonObjectSerializer;

/**
 * Created by dong on 2017/6/18.
 */
@JsonComponent
public class User {
  public static class Serializer extends JsonObjectSerializer<User> {

    @Override
    protected void serializeObject(User user, JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider) throws IOException {

    }
  }

  public static class Deserializer extends JsonObjectDeserializer<User> {

    @Override
    protected User deserializeObject(JsonParser jsonParser,
        DeserializationContext deserializationContext, ObjectCodec objectCodec, JsonNode jsonNode)
        throws IOException {
      return null;
    }
    // ...
  }
}
