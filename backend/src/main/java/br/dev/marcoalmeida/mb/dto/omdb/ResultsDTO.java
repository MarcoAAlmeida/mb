package br.dev.marcoalmeida.mb.dto.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResultsDTO {
    @JsonProperty("Search")
    protected List<ResultDTO> results;
    protected Long totalResults;

}
