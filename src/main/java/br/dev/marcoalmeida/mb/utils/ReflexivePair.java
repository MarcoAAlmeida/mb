package br.dev.marcoalmeida.mb.utils;

import java.util.Objects;

public class ReflexivePair<S> {
    private S first;
    private S second;

    public ReflexivePair(S first, S second) {
        this.first = first;
        this.second = second;

        if (first.equals(second)){
            throw new RuntimeException(String.format("element [%s] cannot be repeated", first));
        }

    }

    public S getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReflexivePair<?> that = (ReflexivePair<?>) o;
        return Objects.equals(first, that.first) && Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
