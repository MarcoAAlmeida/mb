package br.dev.marcoalmeida.mb.repository;

import br.dev.marcoalmeida.mb.domain.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
    Long countByPlayer_IdAndFinishedAtIsNull(Integer playerId);

    @Query("SELECT g FROM Game g LEFT JOIN FETCH g.rounds r WHERE g.id = :gameId AND g.finishedAt IS NULL")
    Optional<Game> findByIdAndFinishedAtIsNull(Integer gameId);

    Optional<Game> findByIdAndFinishedAtIsNotNull(Integer gameId);

    Optional<Game> findByPlayer_IdAndFinishedAtIsNull(Integer gameId);
}