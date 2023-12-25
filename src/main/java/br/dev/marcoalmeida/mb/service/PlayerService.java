package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.dto.PlayerDTO;
import br.dev.marcoalmeida.mb.dto.UnfinishedGameByPlayerDTO;
import br.dev.marcoalmeida.mb.exception.MbException;
import br.dev.marcoalmeida.mb.mapper.PlayerMapper;
import br.dev.marcoalmeida.mb.repository.GameRepository;
import br.dev.marcoalmeida.mb.repository.PlayerRepository;
import br.dev.marcoalmeida.mb.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private static final String UNFINISHED_GAME_NOT_FOUND = "could not find unfinished Game for playerId [%s]";
    private static final String PLAYER_USERNAME_NOT_FOUND = "could not find Player with username [%s]";
    private final GameRepository gameRepository;
    private final RoundRepository roundRepository;

    private final PlayerRepository playerRepository;

    public UnfinishedGameByPlayerDTO getUnfinishedGameByPlayer(Integer playerId) {
        return gameRepository.findByPlayer_IdAndFinishedAtIsNull(playerId)
                .flatMap(game -> roundRepository.findByGame_IdAndAnsweredAtIsNull(game.getId())
                        .map(round-> UnfinishedGameByPlayerDTO.of(game.getId(), round.getId())))
                .orElseThrow(() -> new MbException(String.format(String.format(UNFINISHED_GAME_NOT_FOUND, playerId))));
    }

    public PlayerDTO getByUsername(String username){
        return playerRepository.findByName(username)
                .map(PlayerMapper.INSTANCE::convert)
                .orElseThrow(() -> new MbException(String.format(String.format(PLAYER_USERNAME_NOT_FOUND, username))));
    }
}
