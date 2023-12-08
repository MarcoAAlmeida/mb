package br.dev.marcoalmeida.mb.repository;

import br.dev.marcoalmeida.mb.domain.Game;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
    Long countByPlayer_IdAndFinishedAtIsNull(@Param("playerId")Integer playerId);

    @Query("SELECT g FROM Game g LEFT JOIN FETCH g.rounds r WHERE g.id = :gameId AND g.finishedAt IS NULL")
    Optional<Game> findByIdAndFinishedAtIsNull(@Param("gameId")Integer gameId);

    Optional<Game> findByIdAndFinishedAtIsNotNull(@Param("gameId")Integer gameId);

    Optional<Game> findByPlayer_IdAndFinishedAtIsNull(@Param("gameId")Integer gameId);
}