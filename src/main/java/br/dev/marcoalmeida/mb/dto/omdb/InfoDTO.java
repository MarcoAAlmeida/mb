package br.dev.marcoalmeida.mb.dto.omdb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class InfoDTO {
    @JsonProperty("Year")
    protected Long year;

    @JsonProperty("BoxOffice")
    protected String boxOffice;

    protected Double imdbRating;
    protected Long imdbVotes;
    
    @JsonProperty("Plot")
    protected String plot;

    @JsonCreator
    public InfoDTO(@JsonProperty("Year") String year,
                   @JsonProperty("BoxOffice") String boxOffice,
                   @JsonProperty("imdbRating") String imdbRating,
                   @JsonProperty("imdbVotes") String imdbVotes) {
        this.year = parseReleaseYear(year);
        this.boxOffice = boxOffice;
        this.imdbRating = parseImdbRating(imdbRating);
        this.imdbVotes = parseImdbVotes(imdbVotes);
    }

    private static Double parseImdbRating(String imdbRating) {
        try {
            return Double.parseDouble(imdbRating);

        } catch (NumberFormatException e) {
            log.error("while parsing imdbRating [{}] into Double", imdbRating);
            return null;
        }
    }

    private static Long parseReleaseYear(String releaseYear) {
        try {
            return Long.parseLong(releaseYear);

        } catch (NumberFormatException e) {
            log.error("while parsing releaseYear [{}] into Long", releaseYear);
            return null;
        }
    }

    private static Long parseImdbVotes(String imdbVotes) {
        try {
            return Long.parseLong(imdbVotes.replace(",", ""));

        } catch (NumberFormatException e) {
            log.error("while parsing imdbVotes [{}] into Long", imdbVotes);
            return null;
        }
    }
}

