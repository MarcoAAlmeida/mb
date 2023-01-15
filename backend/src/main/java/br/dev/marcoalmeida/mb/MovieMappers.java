package br.dev.marcoalmeida.mb;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.dto.omdb.DetailDTO;
import br.dev.marcoalmeida.mb.dto.omdb.ResultDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMappers {

    public static List<Movie> from(List<ResultDTO> results){
        return results.stream()
                .map(dto -> Movie.of(dto.getImdbID(), dto.getTitle(), 0.0, 0L))
                .collect(Collectors.toUnmodifiableList());
    }

    public static Movie enrichWith(Movie movie, DetailDTO detailDTO){
        movie.setImdbRating(Double.valueOf(detailDTO.getImdbRating()));
        movie.setImdbVotes(Long.valueOf(detailDTO.getImdbVotes().replaceAll(",", "")));

        return movie;
    }

}
