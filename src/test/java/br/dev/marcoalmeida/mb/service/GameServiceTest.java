package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.MbAbstractTest;
import br.dev.marcoalmeida.mb.dto.GameOverDTO;
import br.dev.marcoalmeida.mb.dto.NewGameDTO;
import br.dev.marcoalmeida.mb.exception.MbException;
import br.dev.marcoalmeida.mb.repository.GameRepository;
import br.dev.marcoalmeida.mb.repository.PlayerRepository;
import br.dev.marcoalmeida.mb.repository.RoundRepository;

//import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import java.util.Optional;

import static br.dev.marcoalmeida.mb.service.GameService.COULD_NOT_START_GAME;
import static br.dev.marcoalmeida.mb.service.GameService.COULD_NOT_STOP_GAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GameServiceTest extends MbAbstractTest {
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private RoundRepository roundRepository;
    @Mock
    private RoundService roundService;

    @InjectMocks
    private GameService gameService;


    @Test
    public void WhenGameStart_NewGameDTOReturned(){
        when(playerRepository.findById(PLAYER1_ID)).thenReturn(Optional.of(PLAYER_1));
        when(gameRepository.countByPlayer_IdAndFinishedAtIsNull(PLAYER1_ID)).thenReturn(0L);
        when(gameRepository.save(any())).thenReturn(GAME1);
        when(roundService.createNewRound(GAME1_ID)).thenReturn(Optional.of(ROUND1));

        NewGameDTO newGameDTO = gameService.start(PLAYER1_ID);

        assertThat(newGameDTO).isNotNull();
        assertThat(newGameDTO.getGameId()).isEqualTo(GAME1.getId());

    }

    @Test
    public void WhenGameStop_GameOverDTOReturned() {
        when(gameRepository.findByIdAndFinishedAtIsNull(GAME1_ID)).thenReturn(Optional.of(GAME1));
        when(gameRepository.save(any())).thenReturn(GAME1);
        
        GameOverDTO gameOverDTO = gameService.stop(GAME1_ID);
        
        assertThat(gameOverDTO).isNotNull();
        assertThat(gameOverDTO.getGameId()).isEqualTo(GAME1.getId());        
    }

    @Test
    public void WhenGameStartPlayerNotFound_MbExceptionIsThrown(){
        when(playerRepository.findById(PLAYER1_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameService.start(PLAYER1_ID))
                .isInstanceOf(MbException.class)
                .hasMessage(String.format(COULD_NOT_START_GAME, PLAYER1_ID));
    }

    @Test
    public void WhenGameStopPlayerNotFound_MbExceptionIsThrown(){
    	when(gameRepository.findByIdAndFinishedAtIsNull(GAME1_ID)).thenReturn(Optional.empty());
    	
    	assertThatThrownBy(() -> gameService.stop(GAME1_ID))
    		.isInstanceOf(MbException.class)
    		.hasMessage(String.format(COULD_NOT_STOP_GAME, GAME1_ID));
    }

    @Test
    public void WhenGameStartPlayerHasUnfinishedGame_MbExceptionIsThrown(){
        when(playerRepository.findById(PLAYER1_ID)).thenReturn(Optional.of(PLAYER_1));
        when(gameRepository.countByPlayer_IdAndFinishedAtIsNull(PLAYER1_ID)).thenReturn(1L);
        
        assertThatThrownBy(() -> gameService.start(PLAYER1_ID))
		.isInstanceOf(MbException.class)
		.hasMessage(String.format(COULD_NOT_START_GAME, PLAYER1_ID));
        
    }

    @Test
    public void WhenGameOverExists_GameOverDTOReturned(){

		  when(gameRepository.findByIdAndFinishedAtIsNotNull(FINISHED_GAME_ID))
		  	.thenReturn(Optional.of(FINISHED_GAME));
		  when(roundRepository.findByGame_IdAndAnsweredAtIsNull(FINISHED_GAME_ID))
		  	.thenReturn(Optional.of(ROUND1));
		  
		  GameOverDTO gameOverDTO = gameService.getGameOver(FINISHED_GAME_ID);
		  
		  assertThat(gameOverDTO.getFinishedAt()).isNotNull();
		  assertThat(gameOverDTO).isNotNull();
		  assertThat(gameOverDTO.getGameId()).isEqualTo(FINISHED_GAME.getId());

    }

    @Test
    public void WhenGameOverDoesNotExist_MbExceptionIsThrown(){
        when(gameRepository.findByIdAndFinishedAtIsNotNull(GAME1_ID))
        	.thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> gameService.getGameOver(GAME1_ID))
		.isInstanceOf(MbException.class)
		.hasMessage(String.format(COULD_NOT_STOP_GAME, GAME1_ID));
    }
}