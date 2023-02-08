package br.dev.marcoalmeida.mb.services;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {
    protected MovieRepository movieRepository;

    public void createMovie(String imdbID, String title, Double imdbRating, Long imdbVotes){
        movieRepository.save(Movie.of(imdbID, title, imdbRating, imdbVotes));
    }

}
