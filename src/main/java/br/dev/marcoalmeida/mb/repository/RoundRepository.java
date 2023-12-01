package br.dev.marcoalmeida.mb.repository;

import br.dev.marcoalmeida.mb.domain.Round;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoundRepository extends CrudRepository<Round, Integer> {
    Optional<Round> findByGame_IdAndAnsweredAtIsNull(Integer gameId);
}
