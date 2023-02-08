package br.dev.marcoalmeida.mb.domain;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor(staticName = "of")
@Data
@Entity
@NoArgsConstructor
public class Movie implements Serializable, Comparable<Movie> {
    @Serial
    private static final long serialVersionUID = -3059376837825602400L;

    @Id
    @Column(name = "imdb_id")
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
