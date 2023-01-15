package br.dev.marcoalmeida.mb.dto.omdb;

import lombok.Data;

@Data
public class DetailDTO {
    protected String imdbID;
    protected String imdbRating;
    protected String imdbVotes;
}
