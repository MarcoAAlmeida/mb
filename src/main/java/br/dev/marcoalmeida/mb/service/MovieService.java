package br.dev.marcoalmeida.mb.service;


import br.dev.marcoalmeida.mb.client.OmdbClient;
import br.dev.marcoalmeida.mb.domain.Movie;
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
                .map(this::saveResult)
                .collect(Collectors.toList());
    }

    private Movie saveResult(SearchResultDTO searchResultDTO) {
        return movieRepository.save(
                Movie.of(searchResultDTO.getImdbID(), searchResultDTO.getTitle(), 0.0, 0L));
    }

}
