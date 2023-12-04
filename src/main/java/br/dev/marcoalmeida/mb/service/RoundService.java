package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.domain.Game;
import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.domain.Round;
import br.dev.marcoalmeida.mb.dto.AnswerDTO;
import br.dev.marcoalmeida.mb.dto.ChoiceEnum;
import br.dev.marcoalmeida.mb.dto.NextRoundDTO;
import br.dev.marcoalmeida.mb.exception.MbException;
import br.dev.marcoalmeida.mb.mapper.MovieMapper;
import br.dev.marcoalmeida.mb.repository.GameRepository;
import br.dev.marcoalmeida.mb.repository.RoundRepository;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static br.dev.marcoalmeida.mb.dto.ChoiceEnum.LEFT;
import static br.dev.marcoalmeida.mb.dto.ChoiceEnum.RIGHT;
import static br.dev.marcoalmeida.mb.utils.FormatterUtils.formatterUI;

@Service
@AllArgsConstructor
public class RoundService {

    private static final Integer MAX_ERRORS = 3;

    private static final String NEXT_ROUND_NOT_FOUND = "could not find unanswered Round with id [%s]";
    private static final String COULD_NOT_COMPUTE_NEXT = "could not compute next round for roundId [%s], is the game over?";
    private final GameRepository gameRepository;
    private final RoundRepository roundRepository;

    private final MovieService movieService;

    public Optional<Round> createNewRound(Integer gameId) {
        return gameRepository.findByIdAndFinishedAtIsNull(gameId)
                .flatMap(this::create);

    }

    public NextRoundDTO getNextRound(Integer nextRoundId) {
        return roundRepository.findByIdAndAnsweredAtIsNull(nextRoundId)
                .map(round -> NextRoundDTO.builder()
                        .gameId(round.getGame().getId())
                        .playerId(round.getGame().getPlayer().getId())
                        .roundId(nextRoundId)
                        .roundCount(roundRepository.countByGame_Id(round.getGame().getId()).intValue())
                        .errorCount(roundRepository.countByGame_IdAndAnsweredAtIsNotNullAndCorrectIsFalse(round.getGame().getId()).intValue())
                        .score(roundRepository.countByGame_IdAndAnsweredAtIsNotNullAndCorrectIsTrue(round.getGame().getId()).intValue())
                        .startedAt(formatterUI().format(round.getGame().getStartedAt()))
                        .left(MovieMapper.INSTANCE.convert(round.getLeft()))
                        .right(MovieMapper.INSTANCE.convert(round.getRight()))
                        .build())
                .orElseThrow(() -> new MbException(String.format(NEXT_ROUND_NOT_FOUND, nextRoundId)));
    }

    public AnswerDTO getAnswer(Integer roundId, ChoiceEnum choice){
        return roundRepository.findByIdAndAnsweredAtIsNull(roundId)
                .flatMap(round -> this.evaluate(round, choice))
                .orElseThrow(()-> new MbException(String.format(COULD_NOT_COMPUTE_NEXT, roundId, choice)));

    }

    private Optional<AnswerDTO> evaluate(Round round, ChoiceEnum choice) {
        round.setAnsweredAt(LocalDateTime.now());
        round.setCorrect(evaluate(round.getLeft().rank(), round.getRight().rank(), choice));
        roundRepository.save(round);

        Integer gameId = round.getGame().getId();

        if (roundRepository.countByGame_IdAndAnsweredAtIsNotNullAndCorrectIsFalse(gameId) >= MAX_ERRORS){
            return gameRepository.findById(gameId)
                            .flatMap(game -> {
                                game.setFinishedAt(LocalDateTime.now());
                                gameRepository.save(game);
                                return Optional.empty();
                            });
        }

        return this.createNewRound(gameId)
                .map(nextRound -> AnswerDTO.builder()
                        .answerRoundId(round.getId())
                        .correctAnswer(round.isCorrect())
                        .nextRoundId(nextRound.getId())
                        .build());


    }

    private boolean evaluate(double left, double right, ChoiceEnum choice) {
        return left == right || (((left > right) && choice == LEFT) || ((right > left) && choice == RIGHT));
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
