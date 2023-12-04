package br.dev.marcoalmeida.mb.request;

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
    private Long page;
}
