package br.dev.marcoalmeida.mb.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Player {

    @Id
    @EqualsAndHashCode.Include
    private Integer id;

    private String name;

}
