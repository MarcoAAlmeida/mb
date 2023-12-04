package br.dev.marcoalmeida.mb.steps;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.domain.Player;
import br.dev.marcoalmeida.mb.dto.AnswerDTO;
import br.dev.marcoalmeida.mb.dto.ChoiceEnum;
import br.dev.marcoalmeida.mb.dto.GameOverDTO;
import br.dev.marcoalmeida.mb.dto.NewGameDTO;
import br.dev.marcoalmeida.mb.dto.NextRoundDTO;
import br.dev.marcoalmeida.mb.dto.UnfinishedGameByPlayerDTO;
import br.dev.marcoalmeida.mb.dto.csv.MovieDTO;
import br.dev.marcoalmeida.mb.repository.MovieRepository;
import br.dev.marcoalmeida.mb.repository.PlayerRepository;
import br.dev.marcoalmeida.mb.utils.ObjectMapperUtils;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static br.dev.marcoalmeida.mb.dto.ChoiceEnum.LEFT;
import static br.dev.marcoalmeida.mb.dto.ChoiceEnum.RIGHT;
import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class MbStepDefinitions {

    @LocalServerPort
    Integer localPort;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private NextRoundDTO nextRoundDTO;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = localPort;
        RestAssured.basePath = "/";
    }

    @Given("the database has default startup data")
    public void ensureDatabaseHasDefaultStartupData() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE;");

        Stream.of("ROUND", "MOVIE", "GAME", "PLAYER")
                .forEach(table -> jdbcTemplate.execute("TRUNCATE TABLE " + table));

        Stream.of("GAME_SEQ", "ROUND_SEQ")
                .forEach(sequence -> jdbcTemplate.execute("ALTER SEQUENCE " + sequence + " RESTART WITH 1"));

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE;");
    }

    @And("Player {int} checks for unfinished game with error {string}")
    public void checkUnfinishedGame(int playerId, String errorMessage) {
        // formatter off
        getSpecWithAuth(playerId)
                .pathParam("playerId", playerId).
            when()
                .get("player/{playerId}/unfinishedGame").
            then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .body("errors", hasItems(errorMessage));
        // formatter on
    }

    @Then("Player {int} checks for unfinished game:")
    public void checkUnfinishedGame(int playerId, UnfinishedGameByPlayerDTO unfinishedGameByPlayerDTOList) {
        // formatter off
        getSpecWithAuth(playerId)
                .pathParam("playerId", playerId).
            when()
                .get("player/{playerId}/unfinishedGame").
            then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo(ObjectMapperUtils.asJson(unfinishedGameByPlayerDTOList)));
        // formatter on
    }

    @When("Player {int} start a new game")
    public void startNewGame(int playerId, NewGameDTO newGameDTO) {
        // formatter off
        getSpecWithAuth(playerId)
                .pathParam("playerId", playerId).
            when()
                .post("game/start/{playerId}").
            then()
                .statusCode(HttpStatus.CREATED.value())
                .body(equalTo(ObjectMapperUtils.asJson(newGameDTO)));
        // formatter on
    }

    @And("Player {int} gets round {int}")
    public void getRound(int playerId, int nextRoundId, NextRoundDTO expectedNextRoundDTO) {
        String fromServer = getNextRoundJson(playerId, nextRoundId);

        assertThatJson(fromServer)
                .whenIgnoringPaths("startedAt", "left", "right")
                .isEqualTo(ObjectMapperUtils.asJson(expectedNextRoundDTO));

        nextRoundDTO = getNextRoundDTO(fromServer);

    }


    @When("Player {int} chooses correctly for round {int}")
    public void chooseCorrect(int playerId, int roundId, AnswerDTO answerDTO) {
        Optional.ofNullable(nextRoundDTO).ifPresentOrElse(
                dto -> this.answerCorrectly(playerId, roundId, answerDTO, dto),
                () -> this.answerCorrectly(playerId, roundId, answerDTO, getNextRoundDTO(getNextRoundJson(playerId, roundId)) )
        );
    }

    @When("Player {int} chooses incorrectly for round {int}")
    public void chooseIncorrect(int playerId, int roundId, AnswerDTO answerDTO) {
        Optional.ofNullable(nextRoundDTO).ifPresentOrElse(
                dto -> this.answerIncorrectly(playerId, roundId, answerDTO, dto),
                () -> this.answerIncorrectly(playerId, roundId, answerDTO, getNextRoundDTO(getNextRoundJson(playerId, roundId)) )
        );
    }

    @When("Player {int} makes last mistake for round {int} with error message {string}")
    public void makeLastMistake(int playerId, int roundId, String errorMessage) {
        Optional.ofNullable(nextRoundDTO).ifPresentOrElse(
                dto -> this.makeLastMistake(playerId, roundId, errorMessage, dto),
                () -> this.makeLastMistake(playerId, roundId, errorMessage, getNextRoundDTO(getNextRoundJson(playerId, roundId)) )
        );
    }

    @When("Player {int} stops current game")
    public void stopGame(int playerId, GameOverDTO gameOverDTO) {
        // formatter off
        String fromServer =
                getSpecWithAuth(playerId)
                        .pathParam("playerId", playerId).
                        when()
                        .put("game/stop/{playerId}").
                        then()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .asString();
        // formatter on

        assertThatJson(fromServer)
                .whenIgnoringPaths("startedAt", "finishedAt")
                .isEqualTo(ObjectMapperUtils.asJson(gameOverDTO));


    }

    @And("that Players are")
    public void addPlayers(List<Player> players) {
        playerRepository.saveAll(players);

    }

    @And("that Movies are")
    public void addMovies(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }


    private void answerCorrectly(int playerId, int roundId, AnswerDTO answerDTO, NextRoundDTO dto) {
        answer(playerId, roundId, answerDTO, getCorrect(dto.getLeft(), dto.getRight()));
    }

    private void answerIncorrectly(int playerId, int roundId, AnswerDTO answerDTO, NextRoundDTO dto) {
        answer(playerId, roundId, answerDTO, getIncorrect(dto.getLeft(), dto.getRight()));
    }

    private void answer(int playerId, int roundId, AnswerDTO answerDTO, ChoiceEnum choice) {
        // formatter off
        getSpecWithAuth(playerId)
                .pathParam("roundId", roundId)
                .pathParam("choice", choice.name()).
            when()
                .put("round/{roundId}/answer/{choice}").
            then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo(ObjectMapperUtils.asJson(answerDTO)));
        // formatter on
    }

    private void makeLastMistake(int playerId, int roundId, String errorMessage, NextRoundDTO currentRound) {
        // formatter off
        getSpecWithAuth(playerId)
                .pathParam("roundId", roundId)
                .pathParam("choice", getIncorrect(currentRound.getLeft(), currentRound.getRight())).
            when()
                .put("round/{roundId}/answer/{choice}").
            then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .body("errors", hasItems(errorMessage));
        // formatter on
    }

    private ChoiceEnum getCorrect(MovieDTO left, MovieDTO right) {
        return ((left.getRating() * left.getVotes()) > (right.getRating() * right.getVotes())) ? LEFT : RIGHT;
    }

    private ChoiceEnum getIncorrect(MovieDTO left, MovieDTO right) {
        return ((left.getRating() * left.getVotes()) > (right.getRating() * right.getVotes())) ? RIGHT : LEFT;
    }


    private RequestSpecification getSpecWithAuth(Integer playerId) {
        return playerRepository.findById(playerId)
                .map(player -> given().auth().form("player1", "player1",
                        new FormAuthConfig("/login", "username", "password")
                                .withLoggingEnabled()))
                .orElseThrow();


    }
    private static NextRoundDTO getNextRoundDTO(String fromServer) {
        return (NextRoundDTO) ObjectMapperUtils.asObject(fromServer, NextRoundDTO.class);
    }

    private String getNextRoundJson(int playerId, int nextRoundId) {
        return getSpecWithAuth(playerId)
                .pathParam("nextRoundId", nextRoundId).
                when()
                .get("round/next/{nextRoundId}").
                then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
    }
}
