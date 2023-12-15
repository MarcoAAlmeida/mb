package br.dev.marcoalmeida.mb.dto.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class SearchResultDTO {
    protected String imdbID;

    @JsonProperty("Title")
    protected String title;

    @JsonProperty("Poster")
    protected String Poster;
}
