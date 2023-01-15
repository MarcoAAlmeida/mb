package br.dev.marcoalmeida.mb.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class Movie implements Comparable<Movie> {
    @CsvBindByName(column = "imdb_id")
    private String imdbID;

    @CsvBindByName(column = "Title")
    private String title;

    @CsvBindByName(column = "imdb_rating")
    protected Double imdbRating;

    @CsvBindByName(column = "imdb_votes")
    protected Long imdbVotes;

    @Override
    public int compareTo(Movie o) {
        return this.imdbID.compareTo(o.imdbID);
    }
}
