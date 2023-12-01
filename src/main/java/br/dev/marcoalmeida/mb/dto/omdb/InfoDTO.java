package br.dev.marcoalmeida.mb.dto.omdb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InfoDTO {
    @JsonProperty("Year")
    protected String year;

    @JsonProperty("BoxOffice")
    protected String boxOffice;

    protected Double imdbRating;
    protected String imdbVotes;

    @JsonCreator
    public InfoDTO(@JsonProperty("Year") String year,
                   @JsonProperty("BoxOffice") String boxOffice,
                   @JsonProperty("imdbRating") String imdbRating,
                   @JsonProperty("imdbVotes") String imdbVotes) {
        this.year = year;
        this.boxOffice = boxOffice;
        this.imdbRating = parseImdbRating(imdbRating);
        this.imdbVotes = parseImdbVotes(imdbVotes);
    }

    private static Double parseImdbRating(String imdbRating) {
        if ("N/A".equals(imdbRating)) {
            return null;
        }

        try {
            return Double.parseDouble(imdbRating);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private static String parseImdbVotes(String imdbVotes) {
        if ("N/A".equals(imdbVotes)) {
            return null;
        } else {
        	return imdbVotes;
        }

		/*
		 * try { return Double.parseDouble(imdbVotes); } catch (NumberFormatException e)
		 * { return null; }
		 */
    }
}


/*
 * @Data public class InfoDTO {
 * 
 * @JsonProperty("Year") protected String year;
 * 
 * @JsonProperty("BoxOffice") protected String boxOffice;
 * 
 * protected Double imdbRating; protected String imdbVotes;
 * 
 * @JsonCreator public InfoDTO(String imdbRating) { try { this.imdbRating =
 * Double.parseDouble(imdbRating); } catch (NumberFormatException e) {
 * this.imdbRating = null; } } }
 */
