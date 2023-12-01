package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.client.OmdbClient;
import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.dto.csv.MovieDTO;
import br.dev.marcoalmeida.mb.dto.omdb.InfoDTO;
import br.dev.marcoalmeida.mb.dto.omdb.ResultsDTO;
import br.dev.marcoalmeida.mb.dto.omdb.SearchResultDTO;
import br.dev.marcoalmeida.mb.mapper.MovieMapper;
import br.dev.marcoalmeida.mb.repository.MovieRepository;
import br.dev.marcoalmeida.mb.utils.RandomUtils;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
	private final OmdbClient omdbClient;

	private final MovieRepository movieRepository;

	private static final Long MAX_RETRIES = 1000L;
	
	public List<Movie> generateByTitleForMultiplePages(String title, Long pages) {
	    List<Movie> result = new ArrayList<>();
	    Optional<ResultsDTO> resultsDTOOptional = Optional.ofNullable(omdbClient.search(title, 1L).getBody());
	    List<Movie> moviesOne = resultsDTOOptional.map(this::saveResults)
        					.orElse(List.of());
	    result.addAll(moviesOne);
	    
	      Long totalResults = resultsDTOOptional.get().getTotalResults();
		
		  Long pagesAvaliable = 0L; 
		  
		  if(totalResults%10 == 0) { 
			  pagesAvaliable = totalResults/10; 
			  } else { pagesAvaliable = (totalResults/10) + 1; 
		  }
		  
		  	  Long pagesUsed = 0L; 
		  	  
		  if(pages > pagesAvaliable) { 
			  pagesUsed = pagesAvaliable;
		  } else { 
			  pagesUsed = pages; 
			  }
		 
	    
	    
	    for (Long page = 2L; page <= pagesUsed; page++) {
	        resultsDTOOptional = Optional.ofNullable(omdbClient.search(title, page).getBody());
	        //log.info("", page);
	        
	        resultsDTOOptional.ifPresent(resultsDTO -> {
	        	
	            List<Movie> movies = saveResults(resultsDTO);
	            result.addAll(movies);
	        });
	    }

	    return result;
	}
	

	public void generateCSVByTitle(String title, Long pages, Writer writer)
			throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

		List<MovieDTO> movies = generateByTitleForMultiplePages(title, pages).stream()
				.map(MovieMapper.INSTANCE::convert)
				.collect(Collectors.toList());

		if (movies != null) {
			StatefulBeanToCsv<MovieDTO> statefulBeanToCsv = new StatefulBeanToCsvBuilder<MovieDTO>(writer)
					.withSeparator(CSVWriter.DEFAULT_SEPARATOR).build();

			statefulBeanToCsv.write(movies);
		}

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

	private List<Movie> saveResults(ResultsDTO resultsDTO) {
		return resultsDTO.getResults().stream().flatMap(resultItem -> getAdditionalInfo(resultItem).stream())
				.collect(Collectors.toList());
	}

	private Optional<Movie> getAdditionalInfo(SearchResultDTO resultItem) {
		return Optional.ofNullable(omdbClient.getInfo(resultItem.getImdbID()).getBody())
				.map(info -> save(resultItem, info));
	}

	private Movie save(SearchResultDTO resultItem, InfoDTO info) {
		return movieRepository.save(Movie.builder().
				id(resultItem.getImdbID())
				.title(resultItem.getTitle())
				.rating(info.getImdbRating())
				.votes(getImdbVotes(info))
				.posterUrl(resultItem.getPoster())
				.releaseYear(getReleaseYear(info))
				.build());
	}
	
	private static Long getImdbVotes(InfoDTO info) {
		return info.getImdbVotes() != null ? Long.parseLong(info.getImdbVotes().replace(",", "")) : null;
		/*
		 * try { return Long.parseLong(info.getImdbVotes().replace(",", "")); } catch
		 * (NumberFormatException e) { return null; }
		 */
	}

	private static Long getReleaseYear(InfoDTO info) {
		try {
			return Long.parseLong(info.getYear());
		} catch (NumberFormatException e) {
			return null;
		}
	}
		

		
	
		
	

}