package br.dev.marcoalmeida.mb;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;

import java.util.List;
import java.util.Set;

public abstract class AbstractTests {
    public static final String IMDB_1 = "imdb_1";

    protected static final Movie MOVIE_1 = Movie.of(IMDB_1);
    protected static final Movie MOVIE_1_OTHER_INSTANCE = Movie.of(IMDB_1);
    protected static final Movie MOVIE_2 = Movie.of("imdb_2");
    protected static final Movie MOVIE_3 = Movie.of("imdb_3");

    protected static final ReflexivePair<Movie> PAIR_1_2 = ReflexivePair.of(MOVIE_1, MOVIE_2);
    protected static final ReflexivePair<Movie> PAIR_1_2_REVERSED = ReflexivePair.of(MOVIE_2, MOVIE_1);
    protected static final ReflexivePair<Movie> PAIR_1_3 = ReflexivePair.of(MOVIE_1, MOVIE_3);
    protected static final ReflexivePair<Movie> PAIR_2_3 = ReflexivePair.of(MOVIE_2, MOVIE_3);

    protected final List<Movie> MOVIES_EMPTY = List.of();
    protected final List<Movie> MOVIES_SINGLE = List.of(MOVIE_1);
    protected final List<Movie> MOVIES_MINIMAL = List.of(MOVIE_1, MOVIE_2);
    protected final List<Movie> MOVIES_TRIPLE = List.of(MOVIE_1, MOVIE_2, MOVIE_3);

    protected final Set<ReflexivePair<Movie>> USED_EMPTY = Set.of();
    protected final Set<ReflexivePair<Movie>> USED_SINGLE = Set.of(PAIR_1_2);
    protected final Set<ReflexivePair<Movie>> USED_DOUBLE = Set.of(PAIR_1_2,PAIR_1_3);
    protected final Set<ReflexivePair<Movie>> USED_TRIPLE = Set.of(PAIR_1_2,PAIR_1_3,PAIR_2_3);

}

