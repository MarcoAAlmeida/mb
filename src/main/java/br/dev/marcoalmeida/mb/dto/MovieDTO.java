package br.dev.marcoalmeida.mb.dto;

import br.dev.marcoalmeida.mb.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MovieDTO {
    private String imdbId;
    private String title;

    public static MovieDTO of(Movie m){
        return MovieDTO.of(m.getId(), m.getTitle());
    }
}
