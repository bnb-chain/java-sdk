package com.binance.dex.api.client.utils.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.List;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeFactory typeFactory = objectMapper.getTypeFactory();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public static ObjectNode createObjectNode() {
        return objectMapper.createObjectNode();
    }

    public static String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String jsonStr, Class<T> tClass) {
        try {
            return objectMapper.readValue(jsonStr, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> fromJsonToList(String jsonStr, Class<T> tClass) {
        try {
            return objectMapper.readValue(jsonStr, typeFactory.constructCollectionType(List.class, tClass));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static JsonNode toJsonNode(String json) throws Exception {
        return objectMapper.readTree(json);
    }

}
