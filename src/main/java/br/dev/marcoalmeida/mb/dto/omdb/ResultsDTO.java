package br.dev.marcoalmeida.mb.dto.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResultsDTO {
    @JsonProperty("Search")
    protected List<SearchResultDTO> results;
    protected Long totalResults;

}
