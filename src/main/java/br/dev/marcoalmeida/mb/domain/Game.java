package br.dev.marcoalmeida.mb.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Game {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = true)
    private LocalDateTime finishedAt;

    @OneToMany(mappedBy = "game")
    List<Round> rounds;

    @OneToOne
    Player player;

}
