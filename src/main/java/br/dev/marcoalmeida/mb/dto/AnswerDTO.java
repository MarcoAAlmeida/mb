package br.dev.marcoalmeida.mb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDTO {
    private Integer answerRoundId;
    private boolean correctAnswer;
    private Integer nextRoundId;
}
