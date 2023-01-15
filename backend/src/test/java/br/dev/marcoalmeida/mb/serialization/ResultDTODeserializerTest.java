package br.dev.marcoalmeida.mb.serialization;

import br.dev.marcoalmeida.mb.dto.omdb.ResultsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ResultDTODeserializerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Value("search.json")
    ClassPathResource searchResourceFile;

    @Test
    void WhenStaticFileProvided_ObjectDeserialized() throws IOException {
        ResultsDTO object  = objectMapper.readValue(searchResourceFile.getInputStream(), ResultsDTO.class);

        assertThat(object).isNotNull();
        assertThat(object.getResults()).size().isEqualTo(10);
        assertThat(object.getResults().get(0).getImdbID()).isEqualTo("tt0076759");

    }
}
