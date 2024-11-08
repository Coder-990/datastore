package hr.datastore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StavkaPrimkeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStavkaPrimke;

    @ManyToOne
    @JoinColumn(name = "IDPrimke", referencedColumnName = "IDPrimke")
    private PrimkaEntity stavkaPrimkePrimka;

    @ManyToOne
    @JoinColumn(name = "IDRobe", referencedColumnName = "IDRobe")
    private RobaEntity stavkaPrimkeRobe;

    private Integer kolicina;
    private Boolean storno;
    private LocalDate datumStorno;
}
