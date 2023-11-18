package br.dev.marcoalmeida.mb.dto.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SearchResultDTO {
    protected String imdbID;

    @JsonProperty("Title")
    protected String title;
}
