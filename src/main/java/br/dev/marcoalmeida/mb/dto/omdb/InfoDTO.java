package br.dev.marcoalmeida.mb.dto.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InfoDTO {
    @JsonProperty("Year")
    protected Long year;

    @JsonProperty("BoxOffice")
    protected String boxOffice;

    protected Double imdbRating;
    protected String imdbVotes;

}
