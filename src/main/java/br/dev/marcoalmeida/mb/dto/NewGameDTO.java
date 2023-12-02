package br.dev.marcoalmeida.mb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class NewGameDTO {
    private Integer gameId;
    private Integer roundId;
}
