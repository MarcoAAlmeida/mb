package br.dev.marcoalmeida.mb.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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

        // TODO: add a similar test for Movie

    }

    
    @Test
    public void WhenSameElementsDifferentOrder_PairsAreEqual(){
        ReflexivePair<String> p1 = new ReflexivePair<>("one", "two");
        ReflexivePair<String> p2 = new ReflexivePair<>("two", "one");

        assertThat(p1).isEqualTo(p2);

        // TODO: add a similar test for Movie
    }
     

    @Test
    public void WhenSameElementUsedInDifferentPairs_NoExceptionAndPairsAreDifferent(){
        ReflexivePair<String> p1 = new ReflexivePair<>("one", "two");
        ReflexivePair<String> p2 = new ReflexivePair<>("two", "three");

        assertThat(p1).isNotEqualTo(p2);

        // TODO: add a similar test for Movie
    }




}
