package com.hrs.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Json工具
 * @author CJ
 * @since 1.0
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("object to json string error, e:{}", e);
        }
        return null;
    }

    public static <T> T toObj(String source, Class<T> clazz) {
        try {
            return objectMapper.readValue(source, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("json to object error, e:{}", e);
        }
        return null;
    }

    public static <T> T toObj(String source, TypeReference<T> reference) {
        try {
            return objectMapper.readValue(source, reference);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("json to object error, e:{}", e);
        }
        return null;
    }
}
