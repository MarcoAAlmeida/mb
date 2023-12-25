package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.MbAbstractTest;
import br.dev.marcoalmeida.mb.dto.PlayerDTO;
import br.dev.marcoalmeida.mb.dto.UnfinishedGameByPlayerDTO;
import br.dev.marcoalmeida.mb.repository.GameRepository;
import br.dev.marcoalmeida.mb.repository.PlayerRepository;
import br.dev.marcoalmeida.mb.repository.RoundRepository;
import br.dev.marcoalmeida.mb.service.PlayerService;
import br.dev.marcoalmeida.mb.service.RoundService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PlayerControllerTests extends MbAbstractTest {

	@Mock
	private PlayerService playerService;
	
	@Mock
	private GameRepository gameRepository;
	
	@Mock
	private RoundRepository roundRepository;

	@Mock
	private PlayerRepository playerRepository;
	
    @Mock
    private RoundService roundService;

    @InjectMocks
    private PlayerController playerController;

    @Test
    public void WhenUnfinishedGameExists_UnfinishedGameByPlayerDTOReturned() {
    	
    	when(playerService.getUnfinishedGameByPlayer(PLAYER1_ID))
			.thenReturn(UNFINISHED_GAME_BY_PLAYER_DTO);
    	
    	ResponseEntity<UnfinishedGameByPlayerDTO> responseEntity = playerController.getUnfinishedGameByPlayer(PLAYER1_ID);
 	  	
    	assertThat(responseEntity.getBody()).isInstanceOf(UnfinishedGameByPlayerDTO.class);
    	assertThat(responseEntity.getBody()).isEqualTo(UNFINISHED_GAME_BY_PLAYER_DTO);    	
    }

	@Test
	public void WhenPlayerExists_PlayerDTOReturned() {

		when(playerService.getByUsername(PLAYER1_NAME))
				.thenReturn(PLAYER_DTO);

		ResponseEntity<PlayerDTO> responseEntity = playerController.getPlayerDetails(PLAYER1_NAME);

		assertThat(responseEntity.getBody()).isInstanceOf(PlayerDTO.class);
		assertThat(responseEntity.getBody()).isEqualTo(PLAYER_DTO);
	}

}