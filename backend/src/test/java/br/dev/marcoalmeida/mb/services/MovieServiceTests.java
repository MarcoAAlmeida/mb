package br.dev.marcoalmeida.mb.services;

import br.dev.marcoalmeida.mb.AbstractTests;
import br.dev.marcoalmeida.mb.repositories.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MovieServiceTests extends AbstractTests {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieService movieService;

	@Test
	public void testCreate(){
		assertThat(movieRepository.count()).isZero();

		movieService.createMovie("im_1", "title1", 10.0, 1000L);

		assertThat(movieRepository.count()).isEqualTo(1);
	}

}
