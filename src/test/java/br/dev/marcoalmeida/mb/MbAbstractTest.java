package br.dev.marcoalmeida.mb;

import br.dev.marcoalmeida.mb.domain.Game;
import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.domain.Player;
import br.dev.marcoalmeida.mb.domain.Round;
import br.dev.marcoalmeida.mb.dto.GameOverDTO;
import br.dev.marcoalmeida.mb.dto.NewGameDTO;
import br.dev.marcoalmeida.mb.dto.NextRoundDTO;
import br.dev.marcoalmeida.mb.dto.PlayerDTO;
import br.dev.marcoalmeida.mb.dto.UnfinishedGameByPlayerDTO;
import br.dev.marcoalmeida.mb.mapper.MovieMapper;

import java.time.LocalDateTime;

import static br.dev.marcoalmeida.mb.utils.FormatterUtils.formatter;
import static br.dev.marcoalmeida.mb.utils.FormatterUtils.formatterUI;

public abstract class MbAbstractTest {
    protected static Long MOVIE_COUNT = 10L;
    protected static Integer RANDOM_PAGE_1 = 1;
    protected static Integer RANDOM_PAGE_2 = 2;
    protected static Movie MOVIE_1 = Movie.builder().id("id_1").build();
    protected static Movie MOVIE_2 = Movie.builder().id("id_2").build();
    protected static Movie MOVIE_3 = Movie.builder().id("id_3").build();

    protected static Integer PLAYER1_ID = 1;
    protected static String PLAYER1_NAME = "p1";

    protected static Player PLAYER_1 = Player.builder()
            .id(PLAYER1_ID)
            .name(PLAYER1_NAME)
            .build();
    public static final LocalDateTime STARTED_AT = LocalDateTime.of(2023, 11, 28, 0, 0, 0);
    public static final LocalDateTime FINISHED_AT = LocalDateTime.of(2023, 11, 28, 0, 0, 1);
    protected static Integer GAME1_ID = 2;
    protected static Integer FINISHED_GAME_ID = 3;
    protected static Integer UNFINISHED_GAME_ID = 4;
    protected static Game GAME1 = Game.builder()
            .id(GAME1_ID)
            .player(PLAYER_1)
            .startedAt(STARTED_AT)
            .build();
    
    protected static Game FINISHED_GAME = Game.builder()
            .id(FINISHED_GAME_ID)
            .player(PLAYER_1)
            .startedAt(STARTED_AT)
            .finishedAt(FINISHED_AT)
            .build();

    protected static Integer ROUND1_ID = 1;

    protected static Round ROUND1 = Round.builder()
            .id(ROUND1_ID)
            .game(GAME1)
            .left(MOVIE_1)
            .right(MOVIE_2)
            .build();

    protected static NewGameDTO NEW_GAME_DTO = NewGameDTO.of(GAME1_ID, ROUND1_ID);

    protected static GameOverDTO GAME_OVER_DTO = GameOverDTO.builder()
            .gameId(GAME1_ID)
            .startedAt(formatter().format(STARTED_AT))
            .finishedAt(formatter().format(FINISHED_AT))
            .score(1)
            .errorCount(0)
            .roundCount(1)
            .build();

    protected static NextRoundDTO NEXT_ROUND_DTO = NextRoundDTO.builder()
        .gameId(ROUND1.getGame().getId())
            .playerId(PLAYER1_ID)
            .roundId(ROUND1_ID)
            .roundCount(1)
            .errorCount(0)
            .score(0)
            .startedAt(formatterUI().format(STARTED_AT))
            .left(MovieMapper.INSTANCE.convert(MOVIE_1))
            .right(MovieMapper.INSTANCE.convert(MOVIE_2))
            .build();
    
    protected static UnfinishedGameByPlayerDTO UNFINISHED_GAME_BY_PLAYER_DTO = UnfinishedGameByPlayerDTO.builder()
    		.gameId(UNFINISHED_GAME_ID)
    		.roundId(ROUND1_ID)
    		.build();

    protected static PlayerDTO PLAYER_DTO = PlayerDTO.builder()
            .id(PLAYER1_ID)
            .name(PLAYER1_NAME)
            .build();
}
