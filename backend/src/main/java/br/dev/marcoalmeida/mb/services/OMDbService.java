package br.dev.marcoalmeida.mb.services;

import br.dev.marcoalmeida.mb.MovieMappers;
import br.dev.marcoalmeida.mb.clients.OMDbClient;
import br.dev.marcoalmeida.mb.domain.Movie;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OMDbService {
    protected OMDbClient omdbClient;

    public List<Movie> search(String tag){
        return MovieMappers.from(omdbClient.search(tag).getBody().getResults()).stream()
                .map(movie -> MovieMappers.enrichWith(movie, omdbClient.detail(movie.getImdbID()).getBody()))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Movie> search(String... tags){
        return Arrays.stream(tags)
                .flatMap(tag -> search(tag).stream())
                .collect(Collectors.toUnmodifiableList());
    }

    public void save(List<Movie> movies, String path) {
        try (Writer writer = new OutputStreamWriter(Files.newOutputStream(Path.of(path)))) {
            StatefulBeanToCsv<Movie> beanToCsv = new StatefulBeanToCsvBuilder<Movie>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(';')
                    .build();
            beanToCsv.write(movies);

        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e.getMessage());
        }
    }


}
