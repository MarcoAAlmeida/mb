package br.dev.marcoalmeida.mb.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Round {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @ManyToOne(optional = false)
    Game game;

    @OneToOne(optional = false)
    private Movie right;

    @OneToOne(optional = false)
    private Movie left;

    @Column(nullable = true)
    private boolean correct;

    @Column(nullable = true)
    private LocalDateTime answeredAt;

}
