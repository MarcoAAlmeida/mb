package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.dto.MovieDTO;
import br.dev.marcoalmeida.mb.mapper.MovieMapper;
import br.dev.marcoalmeida.mb.request.LoadByTitleRequest;
import br.dev.marcoalmeida.mb.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movie")
@AllArgsConstructor
public class MovieController {
    private MovieService movieService;
    @PostMapping("/loadByTitle")
    public ResponseEntity<List<MovieDTO>> loadByTitle(@RequestBody LoadByTitleRequest request){
        return ResponseEntity.ok(movieService.loadMoviesByTitle(request.getTitle()).stream()
                .map(MovieMapper.INSTANCE::convert)
                .toList());
    }

}
