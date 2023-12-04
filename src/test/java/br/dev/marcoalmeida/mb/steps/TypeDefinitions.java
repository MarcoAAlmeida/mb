package br.dev.marcoalmeida.mb.steps;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.domain.Player;
import br.dev.marcoalmeida.mb.dto.AnswerDTO;
import br.dev.marcoalmeida.mb.dto.GameOverDTO;
import br.dev.marcoalmeida.mb.dto.NewGameDTO;
import br.dev.marcoalmeida.mb.dto.NextRoundDTO;
import br.dev.marcoalmeida.mb.dto.UnfinishedGameByPlayerDTO;
import io.cucumber.java.DataTableType;

import java.util.Map;
import java.util.Optional;

public class TypeDefinitions {
    @DataTableType
    public UnfinishedGameByPlayerDTO getUnfinishedGameByPlayerDTO(Map<String, String> entry){
        return UnfinishedGameByPlayerDTO.of(extractOptionalInteger(entry, "gameId"),
                extractOptionalInteger(entry, "roundId"));
    }

    @DataTableType
    public NewGameDTO getNewGameDTO(Map<String, String> entry){
        return NewGameDTO.of(extractOptionalInteger(entry, "gameId"),
                extractOptionalInteger(entry, "roundId"));
    }

    @DataTableType
    public AnswerDTO getAnswerDTO(Map<String, String> entry){
        return AnswerDTO.builder()
                .answerRoundId(extractOptionalInteger(entry, "answerRoundId"))
                .nextRoundId(extractOptionalInteger(entry, "nextRoundId"))
                .correctAnswer(extractOptionalBoolean(entry, "correctAnswer"))
                .build();
    }

    @DataTableType
    public GameOverDTO getGameOverDTO(Map<String, String> entry){
        return GameOverDTO.builder()
                .gameId(extractOptionalInteger(entry, "gameId"))
                .roundCount(extractOptionalInteger(entry, "roundCount"))
                .errorCount(extractOptionalInteger(entry, "errorCount"))
                .score(extractOptionalInteger(entry, "score"))
                .build();
    }



    @DataTableType
    public NextRoundDTO getNextRoundDTO(Map<String, String> entry){
        return NextRoundDTO.builder()
                .gameId(extractOptionalInteger(entry, "gameId"))
                .roundId(extractOptionalInteger(entry, "roundId"))
                .score(extractOptionalInteger(entry, "score"))
                .playerId(extractOptionalInteger(entry, "playerId"))
                .errorCount(extractOptionalInteger(entry, "errorCount"))
                .roundCount(extractOptionalInteger(entry, "roundCount"))
                .build();
    }

    @DataTableType
    public Player getPlayer(Map<String, String> entry){
        return Player.builder()
                .id(extractOptionalInteger(entry, "id"))
                .name(extractOptionalString(entry, "name"))
                .build();
    }

    @DataTableType
    public Movie getMovie(Map<String, String> entry){
        return Movie.builder()
                .id(extractOptionalString(entry, "id"))
                .title(extractOptionalString(entry, "name"))
                .rating(extractOptionalDouble(entry, "rating"))
                .votes(extractOptionalLong(entry, "votes"))
                .build();
    }



    private static Boolean extractOptionalBoolean(Map<String, String> entry, String key){
        return Optional.ofNullable(entry.get(key)).map(Boolean::valueOf).orElse(null);
    }

    private static Integer extractOptionalInteger(Map<String, String> entry, String key){
        return Optional.ofNullable(entry.get(key)).map(Integer::valueOf).orElse(null);
    }

    private static Long extractOptionalLong(Map<String, String> entry, String key){
        return Optional.ofNullable(entry.get(key)).map(Long::valueOf).orElse(null);
    }

    private static Double extractOptionalDouble(Map<String, String> entry, String key){
        return Optional.ofNullable(entry.get(key)).map(Double::valueOf).orElse(null);
    }

    private static String extractOptionalString(Map<String, String> entry, String key){
        return Optional.ofNullable(entry.get(key)).orElse(null);
    }


}
