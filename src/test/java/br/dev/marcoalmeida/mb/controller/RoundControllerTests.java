package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.MbAbstractTest;
import br.dev.marcoalmeida.mb.dto.NextRoundDTO;
import br.dev.marcoalmeida.mb.service.RoundService;
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
public class RoundControllerTests extends MbAbstractTest {

    @Mock
    private RoundService roundService;

    @InjectMocks
    private RoundController roundController;

    @Test
    public void WhenNextRoundExists_NextRoundDTOReturned() {
        when(roundService.getNextRound(ROUND1_ID)).thenReturn(NEXT_ROUND_DTO);

        ResponseEntity<NextRoundDTO> responseEntity = roundController.getNextRound(ROUND1_ID);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(NEXT_ROUND_DTO);

        verify(roundService).getNextRound(ROUND1_ID);
    }





}
