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
@ToString(onlyExplicitlyIncluded = true)
public class Game {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GAME_SEQ")
    @SequenceGenerator(name="GAME_SEQ", allocationSize=1)
    @ToString.Include
    private Integer id;

    @Column(nullable = false)
    @ToString.Include
    private LocalDateTime startedAt;

    @Column(nullable = true)
    @ToString.Include
    private LocalDateTime finishedAt;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    List<Round> rounds;

    @OneToOne
    @ToString.Include
    Player player;

}
