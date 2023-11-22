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

    public void mockingServerClient(String parameterSearch, String parameterMovie, String fileData) {
    	mockServerClient
		    .when(request()
		        .withQueryStringParameter(parameterSearch, parameterMovie)
		        .withMethod("GET")
		    ).respond(response()
		        .withStatusCode(200)
		        .withContentType(MediaType.APPLICATION_JSON)
		        .withBody(fileData)
		    );
	}
    
    
    @Test
    public void WhenKeywordSupplied_MoviesArePopulated() throws IOException {
    	String fileData = Files.readString(Path.of("src/test/resources/omdb/search.json"));
    	mockingServerClient("s", "Star", fileData);
    		
    	fileData = Files.readString(Path.of("src/test/resources/omdb/tt0076759.json"));
    	mockingServerClient("i", "tt0076759", fileData);

    	fileData = Files.readString(Path.of("src/test/resources/omdb/tt0080684.json"));
    	mockingServerClient("i", "tt0080684", fileData);


        assertThat(movieRepository.count()).isEqualTo(0L);

        List<Movie> movies = movieService.loadMoviesByTitle("Star");

        assertThat(movies).isNotEmpty();
        assertThat(movieRepository.count()).isEqualTo(2);
        assertThat(movies.size()).isEqualTo(2);

        Optional<Movie> m1 = movieRepository.findByTitle("Star Wars: Episode IV - A New Hope");
        assertThat(m1).isPresent();

        Optional<Movie> m2 = movieRepository.findByTitle("Star Wars: Episode V - The Empire Strikes Back");
        assertThat(m2).isPresent();
        
        Movie movieOne = m1.get();
        Movie movieTwo = m2.get();
        
        assertThat(movieOne.getId()).isEqualTo("tt0076759");
        assertThat(movieTwo.getId()).isEqualTo("tt0080684");
        
        assertThat(movieOne.getTitle()).isEqualTo("Star Wars: Episode IV - A New Hope");
        assertThat(movieTwo.getTitle()).isEqualTo("Star Wars: Episode V - The Empire Strikes Back");
        
        assertThat(movieOne.getRating()).isEqualTo(8.6);
        assertThat(movieTwo.getRating()).isEqualTo(8.7);
        
        assertThat(movieOne.getVotes()).isEqualTo(1420107);
        assertThat(movieTwo.getVotes()).isEqualTo(1349018);
        
        assertThat(movieOne.getVotes()).isInstanceOf(Long.class);
        assertThat(movieTwo.getVotes()).isInstanceOf(Long.class);
        
        assertThat(movieOne.getPosterUrl()).isEqualTo("https://m.media-amazon.com/images/M/MV5BOTA5NjhiOTAtZWM0ZC00MWNhLThiMzEtZDFkOTk2OTU1ZDJkXkEyXkFqcGdeQXVyMTA4NDI1NTQx._V1_SX300.jpg");
        assertThat(movieTwo.getPosterUrl()).isEqualTo("https://m.media-amazon.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg");
        
        assertThat(movieOne.getReleaseYear()).isEqualTo(1977);
        assertThat(movieTwo.getReleaseYear()).isEqualTo(1980);
    }
}
