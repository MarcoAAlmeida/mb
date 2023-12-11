/*package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.repository.MovieRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.springtest.MockServerTest;
import org.mockserver.model.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.assertj.core.api.Assertions.assertThat;*/

package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.dto.csv.MovieDTO;
import br.dev.marcoalmeida.mb.repository.MovieRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.opencsv.bean.StatefulBeanToCsv;

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
public class MovieServiceComponentTest {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;

    private MockServerClient mockServerClient;
    
    

    @BeforeEach
    public void setup(){
        movieRepository.deleteAll();
    }
    
    @Test
    public void WhenTitleIsSupplied_MoviesComeWithPlot() throws IOException {
    	mockServerClient
	    .when(request()
		        .withQueryStringParameter("s", "Star")
		        .withMethod("GET")
		    ).respond(response()
		        .withStatusCode(200)
		        .withContentType(MediaType.APPLICATION_JSON)
		        .withBody(Files.readString(Path.of("src/test/resources/omdb/searchPlot.json")))
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
    	
    	Optional<List<MovieDTO>> optionalMovieDTOList = movieService.generateByTitleForMultiplePages("Star", 1L);
    	
    	assertThat(optionalMovieDTOList).isPresent();
    	
    	List<MovieDTO> movieDTOList = optionalMovieDTOList.get();
    	
    	assertThat(movieDTOList).hasSize(1);
    	
    	assertThat(movieDTOList.get(0).getPlot()).isEqualTo("Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire's world-destroying battle station, while also attempting to rescue Princess Leia from the mysterious Darth ...");
    }

    

    /**
    @Test
    public void WhenKeywordSupplied_MoviesArePopulated() throws IOException {
    	
    	mockServerClient("s", "Star", Files.readString(Path.of("src/test/resources/omdb/search.json")));

    	mockServerClient("i", "tt0076759", Files.readString(Path.of("src/test/resources/omdb/tt0076759.json")));

    	mockServerClient("i", "tt0080684", Files.readString(Path.of("src/test/resources/omdb/tt0080684.json")));


        assertThat(movieRepository.count()).isEqualTo(0L);

        Optional<List<MovieDTO>> optionalMovies = movieService.generateByTitleForMultiplePages("Star", 1L);

        assertThat(optionalMovies).isPresent();
        assertThat(movieRepository.count()).isEqualTo(2);
        assertThat(optionalMovies.get().size()).isEqualTo(2);

        Optional<Movie> m1 = movieRepository.findByTitle("Star Wars: Episode IV - A New Hope");
        assertThat(m1).isPresent();

        Optional<Movie> m2 = movieRepository.findByTitle("Star Wars: Episode V - The Empire Strikes Back");
        assertThat(m2).isPresent();
        
        
        Movie movieOne = m1.get();
        assertMovie(movieOne, "tt0076759", "Star Wars: Episode IV - A New Hope", 8.6, 1420107, "https://m.media-amazon.com/images/M/MV5BOTA5NjhiOTAtZWM0ZC00MWNhLThiMzEtZDFkOTk2OTU1ZDJkXkEyXkFqcGdeQXVyMTA4NDI1NTQx._V1_SX300.jpg", 1977);

        Movie movieTwo = m2.get();
        assertMovie(movieTwo, "tt0080684", "Star Wars: Episode V - The Empire Strikes Back", 8.7, 1349018, "https://m.media-amazon.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg", 1980);
        
    }
    
    @Test
    public void WhenReleaseYearComesToBadFormat_MoviesArePopulatedWithoutExeption() throws IOException {
    	mockServerClient("s", "Chainsaw", Files.readString(Path.of("src/test/resources/omdb/searchChainsaw.json")));
    	
    	mockServerClient("i", "tt13616990", Files.readString(Path.of("src/test/resources/omdb/tt13616990.json")));
    	
    	Optional<List<MovieDTO>> optionalMovieDTOS = movieService.generateByTitleForMultiplePages("Chainsaw", 1L);

    	assertThat(optionalMovieDTOS).isPresent();
    	
    	Optional<Movie> m1 = movieRepository.findByTitle("Chainsaw Man");

        assertThat(m1).isPresent();
    	
    	Movie chainsaw = m1.get();
    	
    	assertThat(chainsaw.getReleaseYear()).isNull();
    
    }


    private void mockServerClient(String parameterSearch, String parameterMovie, String fileData) {
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
    
    private void assertMovie(Movie movie, String id, String title, double rating, long votes, String posterUrl, long releaseYear) {
        assertThat(movie.getId()).isEqualTo(id);
        assertThat(movie.getTitle()).isEqualTo(title);
        assertThat(movie.getRating()).isEqualTo(rating);
        assertThat(movie.getVotes()).isEqualTo(votes);
        assertThat(movie.getPosterUrl()).isEqualTo(posterUrl);
        assertThat(movie.getReleaseYear()).isEqualTo(releaseYear);
    }
    **/
 }