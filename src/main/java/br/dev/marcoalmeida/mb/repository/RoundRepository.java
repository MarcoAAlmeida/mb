package br.dev.marcoalmeida.mb.repository;

import br.dev.marcoalmeida.mb.domain.Round;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoundRepository extends CrudRepository<Round, Integer> {
    Optional<Round> findByGame_IdAndAnsweredAtIsNull(Integer gameId);
    @Query("SELECT r FROM Round r LEFT JOIN FETCH r.game g WHERE r.id = :roundId AND r.answeredAt IS NULL")
    Optional<Round> findByIdAndAnsweredAtIsNull(Integer roundId);
    Long countByGame_Id(Integer gameId);
    Long countByGame_IdAndAnsweredAtIsNotNullAndCorrectIsFalse(Integer gameId);
    Long countByGame_IdAndAnsweredAtIsNotNullAndCorrectIsTrue(Integer gameId);

}
