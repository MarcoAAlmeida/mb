package br.dev.marcoalmeida.mb.services;

import br.dev.marcoalmeida.mb.AbstractTests;
import br.dev.marcoalmeida.mb.domain.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest

class OMDbServiceTests extends AbstractTests {

	public static final int DEFAULT_PAGE_SIZE = 10;
	private static String STAR_WARS = "Star Wars";
	private static String INDIANA_JONES = "Indiana Jones";

	@Autowired
	OMDbService omDbService;

	@Test
	void WhenOMDbSearchBySingleTag_ExpectResults() throws JsonProcessingException {
		List<Movie> movies = omDbService.search(STAR_WARS);
		assertThat(movies).isNotNull();
		assertThat(movies).size().isEqualTo(DEFAULT_PAGE_SIZE);
		checkForRatingsAndVotes(movies);
	}

	@Test
	void WhenOMDbSearchByMultipleTags_ExpectResultsConcatenated() {
		List<Movie> movies = omDbService.search(STAR_WARS, INDIANA_JONES);
		assertThat(movies).isNotNull();
		assertThat(movies).size().isEqualTo(DEFAULT_PAGE_SIZE * 2);
		checkForRatingsAndVotes(movies);
	}

	@Test
	void WhenOMDbSaveToClasspath_ExpectNoExceptions() {
		assertThatNoException().isThrownBy(()-> omDbService.save(
				omDbService.search(STAR_WARS), "src/test/resources/data/movies_STAR_WARS.csv"));


	}

	private void checkForRatingsAndVotes(List<Movie> movies){
		movies.forEach(
			movie -> {
				assertThat(movie.getImdbID()).isNotBlank();
				assertThat(movie.getTitle()).isNotBlank();
				assertThat(movie.getImdbRating()).isNotEqualTo(0.0);
				assertThat(movie.getImdbVotes()).isNotEqualTo(0);
			}
		);

	}
}
