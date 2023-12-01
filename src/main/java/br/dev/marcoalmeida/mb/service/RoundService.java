package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.domain.Game;
import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.domain.Round;
import br.dev.marcoalmeida.mb.repository.GameRepository;
import br.dev.marcoalmeida.mb.repository.RoundRepository;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoundService {
    private final GameRepository gameRepository;
    private final RoundRepository roundRepository;

    private final MovieService movieService;

    public Optional<Round> createNewRound(Integer gameId) {
        return gameRepository.findByIdAndFinishedAtIsNull(gameId)
                .flatMap(this::create);

    }

    private Optional<Round> create(Game game) {
        return movieService.findNewPair(extractUsedPairs(game.getRounds()))
                .flatMap(pair -> createWithNewPair(game, pair));

    }

    private Set<ReflexivePair<Movie>> extractUsedPairs(List<Round> roundList){
        return Optional.ofNullable(roundList)
                .map(rounds -> rounds.stream()
                        .map(round -> new ReflexivePair<>(round.getLeft(), round.getRight()))
                        .collect(Collectors.toSet()))
                .orElseGet(Set::of);

    }

    private Optional<Round> createWithNewPair(Game game, ReflexivePair<Movie> newPair) {
        return Optional.of(roundRepository.save(Round.builder()
                .game(game)
                .right(newPair.getFirst())
                .left(newPair.getSecond())
                .build()));

    }

    /**
     *  purpose: creates a new Round for a giver Game (gameId)
     *
     *  method : Round createNewRound(Integer gameId)
     *
     *  - retrieve Game by gameId
     *  - retrieve used Set<ReflexivePair<Movie>> usedPairs from the List<Round>
     *  - calls ReflexivePair<Movie> MovieService.findNewPair(Set<ReflexivePair<Movie>> usedPairs)
     *  - create new Round based on newly found Pair
     *
     *  returns : Round
     *
     *
     *
     **/


/**
 *  purpose: returns info need to display next Round
 *
 *  method : getRound(Integer roundId)
 *
 *  returns : NextRoundDTO
 *
 **/

/**
 *  purpose: evaluates if receivedAnswer is correct
 *
 *  method : AnswerRoundDTO evaluate(Integer roundId, ChoiceEnum choice)
 *
 *  - retrieve Round by roundId
 *  - calculate Ranking for left and right movies
 *  - checks if user choice is correct
 *  - marks answeredAt and correct attributes for that Round
 *  - calculates the number of errors
 *  - if equal to 3
 *      - populate Game.finishedAt
 *      - return 422
 *  - if less than 3:
 *      - calls RoundService.createNewRound(Integer gameId)
 *      - returns 200 with AnswerRoundDTO.nextRoundId
 *
 *  returns : 200 - AnswerRoundDTO
 *
 *
 *
 **/


}
