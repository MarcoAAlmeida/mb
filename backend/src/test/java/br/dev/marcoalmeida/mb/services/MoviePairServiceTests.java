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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class MoviePairServiceTests extends AbstractTests {

    @InjectMocks
    MoviePairService moviePairService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void WhenNewPairsArePossible_ExpectNoExceptions() {
        assertThatNoException().isThrownBy(
                () -> assertThat(checkNewPair(MOVIES_MINIMAL)).isEqualTo(PAIR_1_2));
    }

    @Test
    void WhenNewPairsAreNotPossible_ExpectExceptions() {
        assertThatThrownBy(
                () -> checkNewPair(null))
                .isInstanceOf(AssertionError.class);

        assertThatThrownBy(
                () -> checkNewPair(MOVIES_EMPTY))
                .isInstanceOf(AssertionError.class);

        assertThatThrownBy(
                () -> checkNewPair(MOVIES_SINGLE))
                .isInstanceOf(AssertionError.class);
    }


    private ReflexivePair<Movie> checkNewPair(List<Movie> available) throws InterruptedException {
        ReflexivePair<Movie> newPair = moviePairService.pickRandom(available);

        assertThat(newPair).isNotNull();
        assertThat(newPair.getFirst()).isIn(available);
        assertThat(newPair.getSecond()).isIn(available);

        return newPair;
    }
}
