package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.dto.csv.MovieDTO;
import br.dev.marcoalmeida.mb.request.GenerateCSVByTitle;
import br.dev.marcoalmeida.mb.service.MovieService;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

import static br.dev.marcoalmeida.mb.utils.FormatterUtils.formatter;

@RestController
@RequestMapping("movie")
@AllArgsConstructor
public class MovieController {
    private static final String FILE_MASK = "%s_%s.csv";
    private MovieService movieService;
    @PostMapping(value = "/generateCSVByTitle", produces = "text/csv")
    public void generateCSVByTitle(@RequestBody GenerateCSVByTitle request, HttpServletResponse response) throws IOException,
            CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", getTimestampedFileName(request.getTitle())));

        StatefulBeanToCsv<MovieDTO> statefulBeanToCsv = new StatefulBeanToCsvBuilder<MovieDTO>(response.getWriter())
				.withSeparator(CSVWriter.DEFAULT_SEPARATOR).build();
       
        movieService.generateCSVByTitle(request.getTitle(), request.getPage(), statefulBeanToCsv);
    }

    private String getTimestampedFileName(String title) {
        return String.format(FILE_MASK, title,
                formatter().format(LocalDateTime.now()));
    }


}
