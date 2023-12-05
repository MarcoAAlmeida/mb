package br.dev.marcoalmeida.mb.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class GenerateCSVByTitle {
    private String title;

    @Min(value=1)
    private Long page;
}
