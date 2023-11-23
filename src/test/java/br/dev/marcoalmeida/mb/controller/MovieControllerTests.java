package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.request.GenerateCSVByTitle;
import br.dev.marcoalmeida.mb.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTests {
    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @Test
    public void WhenLoadByTitleInvoked_LoadedMoviesAreReturned() {
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        assertThatCode(()->movieController.generateCSVByTitle(GenerateCSVByTitle.of("Star"), mockResponse))
                .doesNotThrowAnyException();

        assertThat(mockResponse.getHeader(HttpHeaders.CONTENT_DISPOSITION)).isNotEmpty();
        assertThat(mockResponse.getHeader(HttpHeaders.CONTENT_DISPOSITION))
                .matches(Pattern.compile("attachment; filename=\"Star_.*"));

    }

    


}
