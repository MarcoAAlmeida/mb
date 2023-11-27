package br.dev.marcoalmeida.mb.repository;

import br.dev.marcoalmeida.mb.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface MovieRepository extends CrudRepository<Movie, String>, PagingAndSortingRepository<Movie, String> {
    Optional<Movie> findByTitle(String title);
    Page<Movie> findByIdNotIn(Set<String> setMovieId, PageRequest pageRequest);
    long countByIdNotIn(Set<String> setMovieId);
}
