package br.dev.marcoalmeida.mb.services;

import br.dev.marcoalmeida.mb.AbstractTests;
import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(
        properties = "game.maxPickRetries=1000"
)
class GameServiceTests extends AbstractTests {

    @InjectMocks
    GameService gameService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        gameService.setMoviePairService(new MoviePairService());
        gameService.setMaxPickRetries(1000);
    }

    @Test
    void WhenNewPairsArePossible_ExpectNoExceptions() {
        assertThatNoException().isThrownBy(() -> gameService.pick(MOVIES_MINIMAL, USED_EMPTY));

        assertThatNoException().isThrownBy(() ->{
            ReflexivePair<Movie> newPair = gameService.pick(MOVIES_TRIPLE, USED_DOUBLE);
            assertThat(newPair).isEqualTo(PAIR_2_3);
        });

    }

    @Test
    void WhenNewPairsAreNotPossible_ExpectExceptions() {
        assertThatThrownBy(
                () -> gameService.pick(MOVIES_MINIMAL, USED_SINGLE))
                .isInstanceOf(AssertionError.class);

        assertThatThrownBy(
                () -> gameService.pick(MOVIES_TRIPLE, USED_TRIPLE))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void WhenNewPairsAreNotFound_ExpectInterruptedException() {
        MoviePairService mockedMoviePairService = mock(MoviePairService.class);

        doReturn(PAIR_1_2).when(mockedMoviePairService).pickRandom(MOVIES_TRIPLE);
        gameService.setMoviePairService(mockedMoviePairService);

        assertThatThrownBy(
                () -> gameService.pick(MOVIES_TRIPLE, USED_DOUBLE))
                .isInstanceOf(InterruptedException.class);
    }

}
