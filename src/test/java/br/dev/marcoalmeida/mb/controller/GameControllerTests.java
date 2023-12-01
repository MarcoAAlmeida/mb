package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.MbAbstractTest;
import br.dev.marcoalmeida.mb.dto.GameOverDTO;
import br.dev.marcoalmeida.mb.dto.NewGameDTO;
import br.dev.marcoalmeida.mb.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameControllerTests extends MbAbstractTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @Test
    public void WhenGameStarted_NewGameDTOReturned() {

        when(gameService.start(PLAYER1_ID)).thenReturn(NEW_GAME_DTO);

        ResponseEntity<NewGameDTO> responseEntity = gameController.start(PLAYER1_ID);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(NEW_GAME_DTO);

        verify(gameService).start(PLAYER1_ID);
    }


    @Test
    public void WhenGameStopped_GameOverDTOReturned() {

        when(gameService.stop(GAME1_ID)).thenReturn(GAME_OVER_DTO);

        ResponseEntity<GameOverDTO> responseEntity = gameController.stop(GAME1_ID);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(GAME_OVER_DTO);

        verify(gameService).stop(GAME1_ID);
    }



}
