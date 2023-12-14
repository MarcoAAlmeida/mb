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
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.http.HttpStatus;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyList;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;



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
    
    private static SearchResultDTO MOVIE_4_SEARCH_RESULTS_DTO = SearchResultDTO.builder().imdbID("tt0076759").title("Star Wars: Episode IV - A New Hope").Poster("https://m.media-amazon.com/images/M/MV5BOTA5NjhiOTAtZWM0ZC00MWNhLThiMzEtZDFkOTk2OTU1ZDJkXkEyXkFqcGdeQXVyMTA4NDI1NTQx._V1_SX300.jpg").build();
    private static ResultsDTO MOVIE_4_RESULTS_DTO = ResultsDTO.builder().results(List.of(MOVIE_4_SEARCH_RESULTS_DTO)).totalResults(MOVIE_COUNT).build();
    private static InfoDTO MOVIE_4_INFO_DTO = new InfoDTO(
    	    "1977",
    	    "$460,998,507",
    	    "8.6",
    	    "1420107",
    	    "Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire's world-destroying battle station, while also attempting to rescue Princess Leia from the mysterious Darth ..."
    	);
    
    
    @Mock
    private StatefulBeanToCsv<MovieDTO> statefulBeanToCsv;
    
    @Mock
    private OmdbClient omdbClient;
    
    @Mock
    private Validator validator; 


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
    
    @Test
    public void WhenTitleIsSupplied_StatefulBeanCSVIsEnvoked() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
    		when(omdbClient.search("Star", 1L))
    			.thenReturn(new ResponseEntity<>(MOVIE_4_RESULTS_DTO, HttpStatus.OK));
    		
    		when(omdbClient.getInfo("tt0076759"))
    			.thenReturn(new ResponseEntity<>(MOVIE_4_INFO_DTO, HttpStatus.OK));

    	    movieService.generateCSVByTitle("Star", 1L, statefulBeanToCsv);
    	
			verify(statefulBeanToCsv, times(1)).write(anyList());
				
			 

    }

}
