package br.dev.marcoalmeida.mb.dto;

import br.dev.marcoalmeida.mb.dto.csv.MovieDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class NextRoundDTO {
    private Integer playerId;
    private Integer gameId;
    private Integer roundId;
    private String startedAt;
    private Integer roundCount;
    private Integer errorCount;
    private Integer score;
    private MovieDTO left;
    private MovieDTO right;

}
