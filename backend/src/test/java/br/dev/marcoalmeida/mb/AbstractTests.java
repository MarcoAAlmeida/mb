package br.dev.marcoalmeida.mb;

import br.dev.marcoalmeida.mb.domain.Movie;

public class AbstractTests {
    public static final String IMDB_1 = "imdb_1";

    protected static final Movie MOVIE_1 = Movie.of(IMDB_1);
    protected static final Movie MOVIE_1_OTHER_INSTANCE = Movie.of(IMDB_1);
    protected static final Movie MOVIE_2 = Movie.of("imdb_2");
    protected static final Movie MOVIE_3 = Movie.of("imdb_3");
}

