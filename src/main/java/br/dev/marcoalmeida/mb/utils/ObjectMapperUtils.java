package br.dev.marcoalmeida.mb.utils;

import br.dev.marcoalmeida.mb.config.ObjectMapperConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class ObjectMapperUtils {

    public static <T> String asJson(T object) {
        return Optional.ofNullable(object)
                .map(obj -> {
                    try {
                        return ObjectMapperConfiguration.instance().writeValueAsString(obj);
                    } catch (JsonProcessingException e) {
                        log.error("while processing {}", object, e);
                        return "";
                    }
                })
                .orElse("");
    }

    public static <T> Object asObject(String json, Class<T> tClass) {
        return Optional.ofNullable(json)
                .map(obj -> {
                    try {
                        return ObjectMapperConfiguration.instance().readValue(json, tClass);
                    } catch (JsonProcessingException e) {
                        log.error("while processing {}", json, e);
                        return null;
                    }
                })
                .orElse(null);
    }


}
