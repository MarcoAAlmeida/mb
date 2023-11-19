package br.dev.marcoalmeida.mb.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Movie {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String title;

    private String posterUrl;

    private Double rating;

    private Long votes;

    private Long releaseYear;

}
