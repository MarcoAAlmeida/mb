package br.dev.marcoalmeida.mb.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MovieTests {

    @Test
    public void WhemEmptyConstructor_NullifiedProperties(){
        Movie m = new Movie();
        assertThat(m.getId()).isNull();
    }

    @Test
    public void WhenEmptyBuilder_NullifiedProperties(){
        Movie m = Movie.builder().build();
        assertThat(m.getId()).isNull();
    }


    @Test
    public void WhenNonEmptyBuilder_NonNullProperties(){
        Movie m = Movie.builder()
                .id("1L")
                .title("testMovie")
                .build();
        assertThat(m.getId()).isNotNull();
        assertThat(m.getTitle()).isNotNull();
    }

    @Test
    public void WhenSameIdentity_ObjectsAreEqual(){
        Movie m1 = Movie.builder()
                .id("1L")
                .title("one")
                .rating(10.0)
                .votes(10L)
                .build();

        Movie m2 = Movie.builder()
                .id("1L")
                .title("two")
                .rating(10.0)
                .votes(10L)
                .build();

        assertThat(m1).isEqualTo(m2);
    }

}
