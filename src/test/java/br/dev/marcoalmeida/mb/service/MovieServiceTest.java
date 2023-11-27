package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.repository.MovieRepository;
import br.dev.marcoalmeida.mb.utils.RandomUtils;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private static Long MOVIE_COUNT = 10L;
    private static Integer RANDOM_PAGE_1 = 1;
    private static Integer RANDOM_PAGE_2 = 2;

    private static Movie MOVIE_1 = Movie.builder().id("id_1").build();
    private static Movie MOVIE_2 = Movie.builder().id("id_2").build();

    private static Movie MOVIE_3 = Movie.builder().id("id_3").build();

    @Test
    public void WhenSelectRandomReflexivePair_ValidPairReturned() {
        try (MockedStatic<RandomUtils> randomUtilsMockedStatic = mockStatic(RandomUtils.class)){
            randomUtilsMockedStatic.when(() -> RandomUtils.randomNaturalNumberUpTo(MOVIE_COUNT))
                    .thenReturn(RANDOM_PAGE_1);

            randomUtilsMockedStatic.when(() -> RandomUtils.randomNaturalNumberUpTo(MOVIE_COUNT-1))
                    .thenReturn(RANDOM_PAGE_2);

            when(movieRepository.countByIdNotIn(eq(Set.of()))).thenReturn(MOVIE_COUNT);
            when(movieRepository.countByIdNotIn(eq(Set.of(MOVIE_1.getId())))).thenReturn(MOVIE_COUNT-1);

            when(movieRepository.findByIdNotIn(eq(Set.of()), eq(PageRequest.of(RANDOM_PAGE_1, 1))))
                    .thenReturn(new PageImpl<>(List.of(MOVIE_1)));

            when(movieRepository.findByIdNotIn(eq(Set.of(MOVIE_1.getId())), eq(PageRequest.of(RANDOM_PAGE_2, 1))))
                    .thenReturn(new PageImpl<>(List.of(MOVIE_2)))
                    .thenReturn(new PageImpl<>(List.of(MOVIE_3)));

            Optional<ReflexivePair<Movie>> selectedMovie = movieService.findNewPair(Set.of(new ReflexivePair<>(MOVIE_1, MOVIE_2)));

            assertThat(selectedMovie).isPresent();
            assertThat(selectedMovie.get()).isEqualTo(new ReflexivePair<Movie>(MOVIE_3, MOVIE_1));

            verify(movieRepository, times(4)).findByIdNotIn(any(), any());
        }
    }


    


}
