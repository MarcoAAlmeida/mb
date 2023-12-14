package br.dev.marcoalmeida.mb.dto.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ResultsDTO {
    @JsonProperty("Search")
    protected List<SearchResultDTO> results;
    protected Long totalResults;

}
