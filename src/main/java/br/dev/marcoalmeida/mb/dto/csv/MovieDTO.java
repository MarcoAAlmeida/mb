package br.dev.marcoalmeida.mb.dto.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    @CsvBindByName
    private String id;

    @CsvBindByName
    private String title;

    @CsvBindByName
    private Double rating;

    @CsvBindByName
    private Long votes;

    @CsvBindByName(column = "RELEASE_YEAR")
    private Long releaseYear;

    @CsvBindByName(column = "POSTER_URL")
    private String posterUrl;
}
