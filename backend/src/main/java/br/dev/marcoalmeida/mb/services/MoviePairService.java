package br.dev.marcoalmeida.mb.services;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class MoviePairService {

    public ReflexivePair<Movie> pickRandom(List<Movie> movieList){
        assertThat(movieList).isNotNull();
        assertThat(movieList).size().isGreaterThan(1);

        List<Movie> shuffledList = Arrays.asList(movieList.toArray(new Movie[movieList.size()]));
        Collections.shuffle(shuffledList);

        return ReflexivePair.of(shuffledList.get(0), shuffledList.get(1));
    }

}
