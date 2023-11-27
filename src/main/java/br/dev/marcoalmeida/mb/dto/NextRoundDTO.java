package br.dev.marcoalmeida.mb.dto;

import br.dev.marcoalmeida.mb.dto.csv.MovieDTO;
import lombok.Data;

@Data
public class NextRoundDTO {
    private Integer playerId;
    private Integer gameId;
    private Integer roundId;
    private String startedAt;
    private Integer roundCount;
    private Integer errorCount;
    private MovieDTO left;
    private MovieDTO right;

    public Integer getScore(){
        return this.roundCount - this.errorCount;
    }

}
