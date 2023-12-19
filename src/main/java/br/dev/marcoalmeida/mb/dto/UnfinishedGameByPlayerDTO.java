package br.dev.marcoalmeida.mb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(staticName = "of")
public class UnfinishedGameByPlayerDTO {
    private Integer gameId;
    private Integer roundId;
}
