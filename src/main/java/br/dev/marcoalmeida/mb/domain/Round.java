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
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROUND_SEQ")
    @SequenceGenerator(name="ROUND_SEQ", allocationSize=1)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY )
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
