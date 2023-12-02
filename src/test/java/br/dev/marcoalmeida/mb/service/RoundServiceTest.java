package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.MbAbstractTest;
import br.dev.marcoalmeida.mb.dto.NextRoundDTO;
import br.dev.marcoalmeida.mb.repository.GameRepository;
import br.dev.marcoalmeida.mb.repository.RoundRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RoundServiceTest extends MbAbstractTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private RoundRepository roundRepository;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private RoundService roundService;

    @Test
    public void WhenCreateNewRound_RoundReturned(){
        // TODO
    }

    @Test
    public void WhenGetNextRound_NextRoundReturned(){
        when(roundRepository.findByIdAndAnsweredAtIsNull(ROUND1_ID)).thenReturn(Optional.of(ROUND1));
        when(roundRepository.countByGame_Id(GAME1_ID)).thenReturn(1L);
        when(roundRepository.countByGame_IdAndAnsweredAtIsNotNullAndCorrectIsFalse(GAME1_ID)).thenReturn(0L);

        NextRoundDTO nextRoundDTO = roundService.getNextRound(ROUND1_ID);

        assertThat(nextRoundDTO).isNotNull();
        assertThat(nextRoundDTO).isEqualTo(NEXT_ROUND_DTO);
    }

    @Test
    public void WhenGetNextRoundNotFound_MbExceptionIsThrown(){
        // TODO
    }


}
