package br.dev.marcoalmeida.mb.repository;

import br.dev.marcoalmeida.mb.domain.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Optional<Player> findByName(String name);
}
