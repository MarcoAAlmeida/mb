package br.dev.marcoalmeida.mb.repository;

import br.dev.marcoalmeida.mb.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
    Long countByPlayer_IdAndFinishedAtIsNull(Integer playerId);
    Optional<Game> findByIdAndFinishedAtIsNull(Integer gameId);
}
