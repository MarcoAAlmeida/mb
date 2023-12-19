package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.MbAbstractTest;
import br.dev.marcoalmeida.mb.dto.UnfinishedGameByPlayerDTO;
import br.dev.marcoalmeida.mb.exception.MbException;
import br.dev.marcoalmeida.mb.repository.GameRepository;
import br.dev.marcoalmeida.mb.repository.RoundRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest extends MbAbstractTest {
	@Mock
    private GameRepository gameRepository;

    @Mock
    private RoundRepository roundRepository;

    @InjectMocks
    private PlayerService playerService;
    
    private static final String UNFINISHED_GAME_NOT_FOUND = "could not find unfinished Game for playerId [%s]";

    @Test
    public void WhenUnfinishedGameExists_UnfinishedGameByPlayerDTOReturned(){
    	
    	when(gameRepository.findByPlayer_IdAndFinishedAtIsNull(PLAYER1_ID))
        	.thenReturn(Optional.of(GAME1));
    	when(roundRepository.findByGame_IdAndAnsweredAtIsNull(GAME1_ID))
    		.thenReturn(Optional.of(ROUND1));
    	
    	UnfinishedGameByPlayerDTO unfinishedGameByPlayerDTO = playerService.getUnfinishedGameByPlayer(PLAYER1_ID);
    	
    	assertThat(unfinishedGameByPlayerDTO).isNotNull();
    	assertThat(unfinishedGameByPlayerDTO.getGameId()).isEqualTo(GAME1.getId());   	
    }

    @Test
    public void WhenUnfinishedGameDoesNotExist_MbExceptionIsThrown(){
    	
    	when(gameRepository.findByPlayer_IdAndFinishedAtIsNull(PLAYER1_ID))
    		.thenReturn(Optional.empty());
    	
    	assertThatThrownBy(() -> playerService.getUnfinishedGameByPlayer(PLAYER1_ID))
        	.isInstanceOf(MbException.class)
        	.hasMessage(String.format(UNFINISHED_GAME_NOT_FOUND, PLAYER1_ID));	
    }

    @Test
    public void WhenPlayerIdDoesNotExist_MbExceptionIsThrown(){
    	
    	when(gameRepository.findByPlayer_IdAndFinishedAtIsNull(PLAYER1_ID))
			.thenReturn(Optional.empty());
	
    	assertThatThrownBy(() -> playerService.getUnfinishedGameByPlayer(PLAYER1_ID))
    		.isInstanceOf(MbException.class)
    		.hasMessage(String.format(UNFINISHED_GAME_NOT_FOUND, PLAYER1_ID));	
    }


}