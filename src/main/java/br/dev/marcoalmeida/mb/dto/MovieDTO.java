package br.dev.marcoalmeida.mb.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MovieDTO {
    private String id;
    private String title;
    private Double rating;
    private Long votes;
    private Long releaseYear;
    private String posterUrl;
}
