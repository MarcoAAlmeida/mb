package br.dev.marcoalmeida.mb.dto.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResultDTO {
    protected String imdbID;

    @JsonProperty("Title")
    protected String title;

    @JsonProperty("Poster")
    protected String Poster;
}
