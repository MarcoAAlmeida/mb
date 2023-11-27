package br.dev.marcoalmeida.mb.service;


import br.dev.marcoalmeida.mb.client.OmdbClient;
import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.dto.omdb.InfoDTO;
import br.dev.marcoalmeida.mb.dto.omdb.ResultsDTO;
import br.dev.marcoalmeida.mb.dto.omdb.SearchResultDTO;
import br.dev.marcoalmeida.mb.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final OmdbClient omdbClient;

    private final MovieRepository movieRepository;

    public List<Movie> loadMoviesByTitle(String title){
        return Optional.ofNullable(omdbClient.search(title).getBody())
                .map(this::saveResults)
                .orElse(List.of());
    }

    private List<Movie> saveResults(ResultsDTO resultsDTO) {
        return resultsDTO.getResults().stream()
                .flatMap(resultItem -> getAdditionalInfo(resultItem).stream())
                .collect(Collectors.toList());
    }

    private Optional<Movie> getAdditionalInfo(SearchResultDTO resultItem) {
        return Optional.ofNullable(omdbClient.getInfo(resultItem.getImdbID()).getBody())
                .map(info -> save(resultItem, info));
    }

    private Movie save(SearchResultDTO resultItem, InfoDTO info){
        return movieRepository.save(Movie.builder()
                        .id(resultItem.getImdbID())
                        .title(resultItem.getTitle())
                        .rating(info.getImdbRating())
                        .votes(Long.parseLong(info.getImdbVotes().replace(",","")))
                        .posterUrl(resultItem.getPoster())
                        .releaseYear(getReleaseYear(info))
                .build());
    }
    
    private static Long getReleaseYear(InfoDTO info) {
        try {
            return Long.parseLong(info.getYear());
        }
        catch(NumberFormatException e) {
            return null;
        }
    }


}
