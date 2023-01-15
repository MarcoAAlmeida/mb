package br.dev.marcoalmeida.mb.domain;

import br.dev.marcoalmeida.mb.AbstractTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MovieTests extends AbstractTests {

    @Test
    void WhenStaticallyConstructed_ExpectValues() {
        assertThat(MOVIE_1.getImdbID()).isEqualTo(IMDB_1);
    }


    @Test
    void WhenCompared_ExpectCorrectOrder() {
        assertThat(MOVIE_1.compareTo(MOVIE_2)).isLessThan(0);
        assertThat(MOVIE_3.compareTo(MOVIE_2)).isGreaterThan(0);
    }

    @Test
    void WhenCompared_ExpectEqual() {
        assertThat(MOVIE_1.equals(MOVIE_1_OTHER_INSTANCE)).isTrue();
        assertThat(MOVIE_1.equals(MOVIE_2)).isFalse();
    }


}
