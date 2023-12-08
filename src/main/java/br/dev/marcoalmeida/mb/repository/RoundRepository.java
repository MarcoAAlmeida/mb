package br.dev.marcoalmeida.mb.repository;

import br.dev.marcoalmeida.mb.domain.Round;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoundRepository extends CrudRepository<Round, Integer> {
    Optional<Round> findByGame_IdAndAnsweredAtIsNull(@Param("gameId")Integer gameId);
    @Query("SELECT r FROM Round r LEFT JOIN FETCH r.game g WHERE r.id = :roundId AND r.answeredAt IS NULL")
    Optional<Round> findByIdAndAnsweredAtIsNull(@Param("roundId")Integer roundId);
    Long countByGame_Id(@Param("roundId")Integer gameId);
    Long countByGame_IdAndAnsweredAtIsNotNullAndCorrectIsFalse(@Param("gameId")Integer gameId);
    Long countByGame_IdAndAnsweredAtIsNotNullAndCorrectIsTrue(@Param("gameId")Integer gameId);

}