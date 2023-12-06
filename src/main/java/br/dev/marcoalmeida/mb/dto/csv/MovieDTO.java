package br.dev.marcoalmeida.mb.dto.csv;

import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class MovieDTO {

    public static final String NEEDS_TITLE = "needs a title";
    public static final String NO_NEGATIVE_RATING = "no negative rating";
    public static final String NEEDS_RATING = "needs rating";
    public static final String NO_NEGATIVE_VOTES = "no negative votes";
    public static final String MAX_VOTES = "voting cannot exceed world´s population";
    public static final String NEEDS_VOTES = "needs votes";
    public static final String MIN_RELEASE_YEAR = "only movies from the 20th century onwards";
    public static final String YEAR_FORMAT = "year must be in 4 digits format";
    public static final String NEEDS_RELEASE_YEAR = "needs release year";
    public static final String NEEDS_A_POSTER_URL = "needs a poster url to be displayed";

    @CsvBindByName
    @EqualsAndHashCode.Include
    private String id;

    @CsvBindByName
    @NotEmpty(message = NEEDS_TITLE)
    private String title;

    @CsvBindByName
    @Min(value = 0, message = NO_NEGATIVE_RATING)
    @Digits(integer = 1, fraction = 1)
    @NotNull(message = NEEDS_RATING)
    private Double rating;

    @CsvBindByName
    @Min(value = 0, message = NO_NEGATIVE_VOTES)
    @Digits(integer = 10, fraction = 0, message = MAX_VOTES)
    @NotNull(message = NEEDS_VOTES)
    private Long votes;

    @CsvBindByName(column = "RELEASE_YEAR")
    @Min(value = 1900, message = MIN_RELEASE_YEAR)
    @Digits(integer = 4, fraction = 0, message = YEAR_FORMAT)
    @NotNull(message = NEEDS_RELEASE_YEAR)
    private Long releaseYear;

    @CsvBindByName(column = "POSTER_URL")
    @NotEmpty(message = NEEDS_A_POSTER_URL)
    private String posterUrl;
}
