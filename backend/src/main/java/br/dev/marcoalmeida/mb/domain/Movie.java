package br.dev.marcoalmeida.mb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class Movie implements Comparable<Movie> {
    private String imdbID;

    @Override
    public int compareTo(Movie o) {
        return this.imdbID.compareTo(o.imdbID);
    }
}
