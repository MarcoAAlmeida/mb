package br.dev.marcoalmeida.mb.repository;

import br.dev.marcoalmeida.mb.domain.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;

    private static final String ID_1 = "id_1";

    private static final Movie MOVIE_1 = Movie.of(ID_1, "movie1", 0.0, 10L);
    private static final Movie MOVIE_1_a = Movie.of(ID_1, "movie1 alternative",0.1, 11L);

    @BeforeEach
    public void setup(){
        movieRepository.deleteAll();
    }

    @Test
    public void WhenCreated_FoundById(){
        assertThat(movieRepository.count()).isEqualTo(0);

        Movie m = movieRepository.save(MOVIE_1);

        assertThat(movieRepository.count()).isEqualTo(1);

        Optional<Movie> optionalMovie = movieRepository.findById(m.getId());

        assertThat(optionalMovie).isPresent();
        assertThat(optionalMovie.get()).isEqualTo(MOVIE_1);
    }

    @Test
    public void WhenSameMovieSavedTwice_LastSavePrevails(){
        assertThat(MOVIE_1).isEqualTo(MOVIE_1_a);

        movieRepository.save(MOVIE_1);

        Optional<Movie> optionalMovie = movieRepository.findById(ID_1);

        assertThat(optionalMovie).isPresent();
        assertThat(optionalMovie.get().getTitle()).isEqualTo("movie1");

        movieRepository.save(MOVIE_1_a);

        optionalMovie = movieRepository.findById(ID_1);

        assertThat(optionalMovie).isPresent();
        assertThat(optionalMovie.get().getTitle()).isEqualTo("movie1 alternative");
    }

    @Test
    public void WhenPreviouslySavedGetsDeleted_NotFoundByID(){
        // check that no movies exist
        // save MOVIE_1_a
        // check that only one movie exists
        // check that you can retrieve it by ID
        // then delete it
        // check that no movies exist
        // check that you cannot retrieve it by ID

    }



}
