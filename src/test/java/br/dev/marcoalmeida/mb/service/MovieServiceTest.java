package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest
@MockServerTest("omdb.server.url=http://localhost:${mockServerPort}")
public class MovieServiceTest {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;

    private MockServerClient mockServerClient;

    @Test
    public void WhenKeywordSupplied_MoviesArePopulated() throws IOException {
        mockServerClient
                .when(request()
                    .withQueryStringParameter("s", "Star")
                    .withMethod("GET")
                ).respond(response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(Files.readString(Path.of("src/test/resources/omdb/search.json")))
                );

        mockServerClient
                .when(request()
                        .withQueryStringParameter("i", "tt0076759")
                        .withMethod("GET")
                ).respond(response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(Files.readString(Path.of("src/test/resources/omdb/tt0076759.json")))
                );

        mockServerClient
                .when(request()
                        .withQueryStringParameter("i", "tt0080684")
                        .withMethod("GET")
                ).respond(response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(Files.readString(Path.of("src/test/resources/omdb/tt0080684.json")))
                );


        assertThat(movieRepository.count()).isEqualTo(0L);

        List<Movie> movies = movieService.generateByTitle("Star");

        assertThat(movies).isNotEmpty();
        assertThat(movieRepository.count()).isEqualTo(2);
        assertThat(movies.size()).isEqualTo(2);

        Optional<Movie> m1 = movieRepository.findByTitle("Star Wars: Episode IV - A New Hope");
        assertThat(m1).isPresent();

        Optional<Movie> m2 = movieRepository.findByTitle("Star Wars: Episode V - The Empire Strikes Back");
        assertThat(m2).isPresent();
    }
}
