package br.dev.marcoalmeida.mb.utils;

import lombok.Getter;

import java.io.Serializable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Getter
public class ReflexivePair<S extends Comparable<S>> implements Serializable {
    private S first;
    private S second;

    public ReflexivePair(S first, S second) {

        assertThat(first).isNotNull();
        assertThat(second).isNotNull();
        assertThat(first.equals(second)).isFalse();

        this.first = first;
        this.second = second;
    }

    public static <S extends Comparable<S>> ReflexivePair<S> of(S first, S second){
        return new ReflexivePair<>(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof ReflexivePair<?> other)) {
            return false;
        }

        return ((this.first.equals(other.first) && this.second.equals(other.second)) ||
                (this.first.equals(other.second) && this.second.equals(other.first)));
    }

    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }
}
