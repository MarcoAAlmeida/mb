package br.dev.marcoalmeida.mb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
public class ErrorDTO {
    List<String> errors;
}
