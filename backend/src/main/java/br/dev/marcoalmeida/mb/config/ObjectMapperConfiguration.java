package br.dev.marcoalmeida.mb.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {
    private static final ObjectMapper OBJECT_MAPPER;

    static{
        OBJECT_MAPPER = new ObjectMapper();

        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public static ObjectMapper instance() { return OBJECT_MAPPER; }

    @Bean
    public ObjectMapper objectMapper() { return OBJECT_MAPPER; }
}
