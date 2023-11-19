package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.dto.MovieDTO;
import br.dev.marcoalmeida.mb.request.LoadByTitleRequest;
import br.dev.marcoalmeida.mb.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTests {
    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @Test
    public void WhenLoadByTitleInvoked_LoadedMoviesAreReturned() {

        Movie m1 = Movie.builder().id("id1").title("title1").build();
        Movie m2 = Movie.builder().id("id2").title("title2").build();

        when(movieService.loadMoviesByTitle(any()))
                .thenReturn(List.of(m1, m2));

        ResponseEntity<List<MovieDTO>> responseEntity = movieController.loadByTitle(
                LoadByTitleRequest.of("Star"));

        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();

        List<MovieDTO> response = responseEntity.getBody();
        assertThat(response).hasSize(2);

        verify(movieService).loadMoviesByTitle("Star");


    }

    


}
