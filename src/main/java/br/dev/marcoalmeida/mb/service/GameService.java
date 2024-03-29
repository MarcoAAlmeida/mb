package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.domain.Game;
import br.dev.marcoalmeida.mb.domain.Player;
import br.dev.marcoalmeida.mb.dto.GameOverDTO;
import br.dev.marcoalmeida.mb.dto.NewGameDTO;
import br.dev.marcoalmeida.mb.exception.MbException;
import br.dev.marcoalmeida.mb.repository.GameRepository;
import br.dev.marcoalmeida.mb.repository.PlayerRepository;
import br.dev.marcoalmeida.mb.repository.RoundRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static br.dev.marcoalmeida.mb.utils.FormatterUtils.formatterUI;

@Service
@AllArgsConstructor
@Slf4j
public class GameService {

    public static final String COULD_NOT_START_GAME = "could not create Game for playerId [%d], check logs";
    public static final String COULD_NOT_STOP_GAME = "could not stop Game with gameId [%d], maybe the game is already finished?";


    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    private final RoundRepository roundRepository;
    private final RoundService roundService;


    public NewGameDTO start(Integer playerId) throws MbException {
        return playerRepository.findById(playerId)
                .flatMap(this::startWithPlayer)
                .orElseThrow(() -> new MbException(String.format(COULD_NOT_START_GAME, playerId)));

    }

    public GameOverDTO stop(Integer gameId) throws MbException {
        return gameRepository.findByIdAndFinishedAtIsNull(gameId)
                .flatMap(this::stop)
                .orElseThrow(() -> new MbException(String.format(COULD_NOT_STOP_GAME, gameId)));

    }

    public GameOverDTO getGameOver(Integer gameId) throws MbException {
        return gameRepository.findByIdAndFinishedAtIsNotNull(gameId)
                .flatMap(this::getOptionalGameOverDTO)
                .orElseThrow(() -> new MbException(String.format(COULD_NOT_STOP_GAME, gameId)));

    }

    private Optional<NewGameDTO> startWithPlayer(Player player) {
        if (gameRepository.countByPlayer_IdAndFinishedAtIsNull(player.getId()) != 0) {
            log.error("Player {} has unfinished games", player);
            return Optional.empty();
        }

        Game game = gameRepository.save(Game.builder()
                .player(player)
                .startedAt(LocalDateTime.now())
                .build());

        return roundService.createNewRound(game.getId())
                .map(round -> NewGameDTO.of(game.getId(), round.getId()));
    }

    private Optional<GameOverDTO> stop(Game game) {
        game.setFinishedAt(LocalDateTime.now());
        gameRepository.save(game);

        return getOptionalGameOverDTO(game);
    }

    private Optional<GameOverDTO> getOptionalGameOverDTO(Game game) {
        roundRepository.findByGame_IdAndAnsweredAtIsNull(game.getId())
                .ifPresent(lastRound -> {
                    lastRound.setAnsweredAt(LocalDateTime.now());
                    roundRepository.save(lastRound);
                });

        return Optional.of(GameOverDTO.builder()
                .gameId(game.getId())
                .startedAt(formatterUI().format(game.getStartedAt()))
                .finishedAt(formatterUI().format(game.getFinishedAt()))
                .roundCount(roundRepository.countByGame_Id(game.getId()).intValue())
                .errorCount(roundRepository.countByGame_IdAndAnsweredAtIsNotNullAndCorrectIsFalse(game.getId()).intValue())
                .score(roundRepository.countByGame_IdAndAnsweredAtIsNotNullAndCorrectIsTrue(game.getId()).intValue())
                .build());
    }

}
