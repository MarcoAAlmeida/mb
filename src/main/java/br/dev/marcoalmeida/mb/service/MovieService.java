package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.client.OmdbClient;
import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.dto.csv.MovieDTO;
import br.dev.marcoalmeida.mb.dto.omdb.InfoDTO;
import br.dev.marcoalmeida.mb.dto.omdb.ResultsDTO;
import br.dev.marcoalmeida.mb.dto.omdb.SearchResultDTO;
import br.dev.marcoalmeida.mb.exception.MbException;
import br.dev.marcoalmeida.mb.repository.MovieRepository;
import br.dev.marcoalmeida.mb.utils.RandomUtils;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.Writer;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

	private static Integer DEFAULT_OMDB_PAGE_SIZE = 10;
	private final OmdbClient omdbClient;
	private final MovieRepository movieRepository;

	private final Validator validator;


	private static final Long MAX_RETRIES = 1000L;
	public void generateCSVByTitle(String title, Long pages, StatefulBeanToCsv<MovieDTO> statefulBeanToCsv){
		generateByTitleForMultiplePages(title, pages)
				.ifPresent(moviesDTOs -> {
					try {
						statefulBeanToCsv.write(moviesDTOs);

					} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
						log.error("could not generate CSV for title [{}] pages[{}] ", title, pages, e);
						throw new MbException(e.getMessage());

					}

				});

	}

	public Optional<List<MovieDTO>> generateByTitleForMultiplePages(String title, Long requestedPages) {
		return fetch(title)
				.map(firstPage -> makeAdditionalFetches(title, firstPage, requestedPages.intValue()));
	}

	public Optional<ReflexivePair<Movie>> findNewPair(Set<ReflexivePair<Movie>> usedPairs) {
		long retryCount = 0;

		while (retryCount < MAX_RETRIES) {
			Optional<ReflexivePair<Movie>> candidate = selectRandomReflexivePair();

			if (candidate.stream().anyMatch(pair -> !usedPairs.contains(pair))) {
				return candidate;
			}

			retryCount++;
		}

		return Optional.empty();

	}

	private List<MovieDTO> makeAdditionalFetches(String title, ResultsDTO firstPage, Integer requestedPages) {
		List<MovieDTO> fromFirstPage = parseResults(firstPage);

		List<MovieDTO> fromAdditionalCalls = getMaxFetches(firstPage, requestedPages)
				.map(maxPages -> IntStream.range(2, maxPages)
						.mapToObj(page -> fetchStreamOfMovieDTOs(title, page))
								.flatMap(Function.identity())
								.collect(Collectors.toList()))
				.orElse(List.of());


		return Stream.concat(fromFirstPage.stream(), fromAdditionalCalls.stream())
				.filter(this::validateMovieDTO)
				.collect(Collectors.toList());
	}

	private boolean validateMovieDTO(MovieDTO movieDTO){
		Set<ConstraintViolation<MovieDTO>> violations = validator.validate(movieDTO);

		if (violations.isEmpty()){
			return true;
		}

		log.error("movieDTO [{}] had these violations [{}]", movieDTO, violations.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(",")));

		return false;

	}

	private Stream<MovieDTO> fetchStreamOfMovieDTOs(String title, Integer page) {
		try{
			return fetch(title, page)
					.map(resultsDTO -> parseResults(resultsDTO).stream())
					.orElseGet(Stream::empty);
		}catch (FeignException e){
			log.error("could not fetch title [{}] page [{}]", title, page, e);

		}
		return Stream.empty();
	}
	private Optional<ResultsDTO> fetch(String title) {
		return fetch(title, 1);
	}

	private Optional<ResultsDTO> fetch(String title, Integer page){
		log.info("fetching title [{}] page [{}]", title, page);

		try{
			return Optional.ofNullable( omdbClient.search(title, page.longValue()).getBody());

		}catch (FeignException e){
			log.error("could not fetch title [{}] page [{}]", title, page, e);

		}

		return Optional.empty();
	}

	private Optional<Integer> getMaxFetches(ResultsDTO firstPage, Integer pages) {
		Long min = Math.min(firstPage.getTotalResults() / DEFAULT_OMDB_PAGE_SIZE + 1, pages);
		return min <= 1 ? Optional.empty() : Optional.of(min.intValue() + 1);

	}

	private List<MovieDTO> parseResults(ResultsDTO resultsDTO) {
		return resultsDTO.getResults().stream().flatMap(resultItem -> getAdditionalInfo(resultItem).stream())
				.collect(Collectors.toList());
	}

	private Optional<MovieDTO> getAdditionalInfo(SearchResultDTO resultItem) {
		log.info("getting addtional info for imdbId [{}]", resultItem.getImdbID());

		try{
			return Optional.ofNullable(omdbClient.getInfo(resultItem.getImdbID()).getBody())
					.map(info -> buildMovieDTO(resultItem, info));

		}catch (FeignException e){
			log.error("while searching for imdbID [{}]", resultItem.getImdbID(), e);
		}

		return Optional.empty();
	}

	private MovieDTO buildMovieDTO(SearchResultDTO resultItem, InfoDTO info) {
		return MovieDTO.builder()
				.id(resultItem.getImdbID())
				.title(resultItem.getTitle())
				.rating(info.getImdbRating())
				.votes(info.getImdbVotes())
				.posterUrl(resultItem.getPoster())
				.releaseYear(info.getYear())
				.plot(info.getPlot())
				.build();
	}



	private Optional<ReflexivePair<Movie>> selectRandomReflexivePair() {
		return selectRandomMovie(Set.of()).flatMap(this::findAnotherMovie);
	}

	private Optional<ReflexivePair<Movie>> findAnotherMovie(Movie m1) {
		return selectRandomMovie(Set.of(m1.getId())).flatMap(m2 -> Optional.of(new ReflexivePair<Movie>(m1, m2)));
	}

	private Optional<Movie> selectRandomMovie(Set<String> setMovieId) {
		return movieRepository
				.findByIdNotIn(setMovieId, PageRequest
						.of(RandomUtils.randomNaturalNumberUpTo(movieRepository.countByIdNotIn(setMovieId)), 1))
				.get().findFirst();
	}
}