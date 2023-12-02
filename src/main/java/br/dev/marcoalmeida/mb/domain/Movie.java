package br.dev.marcoalmeida.mb.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Transient
    public Double rank(){
        return rating * votes;
    }

}
