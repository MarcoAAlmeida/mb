package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.MbAbstractTest;
import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.domain.Round;
import br.dev.marcoalmeida.mb.dto.NextRoundDTO;
import br.dev.marcoalmeida.mb.exception.MbException;
import br.dev.marcoalmeida.mb.repository.GameRepository;
import br.dev.marcoalmeida.mb.repository.RoundRepository;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.any;



import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    
    private static final String NEXT_ROUND_NOT_FOUND = "could not find unanswered Round with id [%s]";

    ReflexivePair<Movie> MOCK_PAIR = new ReflexivePair<>(MOVIE_1, MOVIE_2);
    
    @Test
    public void WhenCreateNewRound_RoundReturned(){
        when(gameRepository.findByIdAndFinishedAtIsNull(GAME1_ID))
        	.thenReturn(Optional.of(GAME1));
        
        when(movieService.findNewPair(anySet()))
        	.thenReturn(Optional.of(MOCK_PAIR));
        
        when(roundRepository.save(any(Round.class)))
        	.thenReturn(ROUND1);
    
        Optional<Round> actualRound = roundService.createNewRound(GAME1_ID);
      
        assertThat(actualRound).isNotEmpty();
        assertThat(actualRound.get()).isEqualTo(ROUND1);
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
    	when(roundRepository.findByIdAndAnsweredAtIsNull(ROUND1_ID))
    		.thenReturn(Optional.empty());
    	
    	assertThatThrownBy(() -> roundService.getNextRound(ROUND1_ID))
    		.isInstanceOfAny(MbException.class)	
    		.hasMessage(String.format(NEXT_ROUND_NOT_FOUND, ROUND1_ID));
  
    }


}