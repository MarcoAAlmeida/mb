package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.client.OmdbClient;
import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.dto.csv.MovieDTO;
import br.dev.marcoalmeida.mb.dto.omdb.InfoDTO;
import br.dev.marcoalmeida.mb.dto.omdb.ResultsDTO;
import br.dev.marcoalmeida.mb.dto.omdb.SearchResultDTO;
import br.dev.marcoalmeida.mb.repository.MovieRepository;
import br.dev.marcoalmeida.mb.utils.RandomUtils;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

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
    MovieRepository movieRepository;
    @Mock
    private StatefulBeanToCsv<MovieDTO> statefulBeanToCsv;

    @Mock
    private Validator validator;
    @Mock
    private OmdbClient omdbClient;

    @InjectMocks
    private MovieService movieService;

    private static Long MOVIE_COUNT = 10L;
    private static Integer RANDOM_PAGE_1 = 1;
    private static Integer RANDOM_PAGE_2 = 2;

    public static final String ID_1 = "id_1";
    private static Movie MOVIE_1 = Movie.builder().id(ID_1).build();
    private static Movie MOVIE_2 = Movie.builder().id("id_2").build();
    private static Movie MOVIE_3 = Movie.builder().id("id_3").build();
    private static SearchResultDTO SEARCH_RESULT_DTO = SearchResultDTO.of(ID_1, "Star", "http://poster.to/poster.jpg");
    private static ResultsDTO RESULT_DTO = ResultsDTO.of(List.of(SEARCH_RESULT_DTO), 1L);

    private static InfoDTO INFO_DTO = InfoDTO.builder().build();

    @Test
    public void WhenGenerateCSVByTitleInvoked_MovieDTOListConvertedToCSV() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        when(omdbClient.search("Star", 1L)).thenReturn(ResponseEntity.of(Optional.of(RESULT_DTO)));
        when(omdbClient.getInfo(ID_1)).thenReturn(ResponseEntity.of(Optional.of(INFO_DTO)));
        when(validator.validate(any())).thenReturn(Set.of());

        movieService.generateCSVByTitle("Star", 1L, statefulBeanToCsv);

        verify(statefulBeanToCsv).write(any(List.class));
    }

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
