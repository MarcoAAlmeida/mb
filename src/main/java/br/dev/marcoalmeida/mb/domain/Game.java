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
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GAME_SEQ")
    @SequenceGenerator(name="GAME_SEQ", allocationSize=1)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = true)
    private LocalDateTime finishedAt;

    @OneToMany(mappedBy = "game")
    List<Round> rounds;

    @OneToOne
    Player player;

}
