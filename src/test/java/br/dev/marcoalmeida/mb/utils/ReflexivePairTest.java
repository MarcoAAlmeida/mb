package br.dev.marcoalmeida.mb.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.dev.marcoalmeida.mb.domain.Movie;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReflexivePairTest {

    @Test
    public void WhenInvalidPairConstructed_RuntimeException() {
        assertThatThrownBy(() -> new ReflexivePair<String>("one", "one"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("element [one] cannot be repeated");

        assertThatThrownBy(() -> new ReflexivePair<Long>(1L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("element [1] cannot be repeated");

        Movie movie1 = Movie.builder().id(1L).title("movieTitle1").build();
        Movie movie2 = Movie.builder().id(1L).title("movieTitle2").build();

        assertThatThrownBy(() -> new ReflexivePair<Movie>(movie1, movie2))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("element [" + movie1 + "] cannot be repeated");
    }


    

    
    @Test
    public void WhenSameElementsDifferentOrder_PairsAreEqual(){
        ReflexivePair<String> p1 = new ReflexivePair<>("one", "two");
        ReflexivePair<String> p2 = new ReflexivePair<>("two", "one");

        assertThat(p1).isEqualTo(p2);

        Movie movie1 = Movie.builder().id(1L).build();
        Movie movie2 = Movie.builder().id(2L).build();

        ReflexivePair<Movie> moviePair1 = new ReflexivePair<>(movie1, movie2);
        ReflexivePair<Movie> moviePair2 = new ReflexivePair<>(movie2, movie1);

        assertThat(moviePair1).isEqualTo(moviePair2);
    }
     

    @Test
    public void WhenSameElementUsedInDifferentPairs_NoExceptionAndPairsAreDifferent(){
        ReflexivePair<String> p1 = new ReflexivePair<>("one", "two");
        ReflexivePair<String> p2 = new ReflexivePair<>("two", "three");

        assertThat(p1).isNotEqualTo(p2);

        Movie movie1 = Movie.builder().id(1L).build();
        Movie movie2 = Movie.builder().id(2L).build();
        Movie movie3 = Movie.builder().id(3L).build();

        ReflexivePair<Movie> moviePair1 = new ReflexivePair<>(movie1, movie2);
        ReflexivePair<Movie> moviePair2 = new ReflexivePair<>(movie2, movie3);

        assertThat(moviePair1).isNotEqualTo(moviePair2);
    }




}
