package com.spring.rate.limit.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@UtilityClass
public class MapperUtil {
    public static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = (new ObjectMapper()).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule());
    }

    public static Optional<String> convertObjectToString(Object object) {
        try {
            return Optional.ofNullable(OBJECT_MAPPER.writeValueAsString(object));
        } catch (Exception exception) {
            log.error("An error occurred while converting object to string", exception);
            return Optional.empty();
        }
    }

    public static <T> Optional<T> convertFromStringToObject(String request, Class<T> clazz) {
        return Optional.ofNullable(request)
                .flatMap(re -> parseRequest(request, clazz));
    }

    private static <T> Optional<T> parseRequest(String request, Class<T> clazz) {
        try {
            T object = OBJECT_MAPPER.readValue(request, clazz);
            return Optional.ofNullable(object);
        } catch (Exception exception) {
            log.error("An error occurred while converting string to object", exception);
            return Optional.empty();
        }
    }
}
