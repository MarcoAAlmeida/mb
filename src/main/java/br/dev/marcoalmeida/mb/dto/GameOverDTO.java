package br.dev.marcoalmeida.mb.dto;

import lombok.Data;

@Data
public class GameOverDTO {
    private Integer gameId;
    private String startedAt;
    private String finshedAt;
    private Integer roundCount;
    private Integer errorCount;
    private Integer score;

}
