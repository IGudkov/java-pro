package ru.inno.java.pro.payment.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class ObjectMapperUtils {
  private final ObjectMapper objectMapper;

  public ObjectMapperUtils(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public <T> List<T> toList(String content, Class<T> clazz) throws JsonProcessingException {
    CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
    return objectMapper.readValue(content, listType);
  }
}
