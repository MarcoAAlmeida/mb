package br.dev.marcoalmeida.mb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameOverDTO {
    private Integer gameId;
    private String startedAt;
    private String finishedAt;
    private Integer roundCount;
    private Integer errorCount;
    private Integer score;

}
