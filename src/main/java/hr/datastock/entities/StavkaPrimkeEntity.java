package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "stavkaprimke", schema = "datastock")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StavkaPrimkeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDStavkaPrimke")
    private Long idStavkaPrimke;

    @ManyToOne
    @JoinColumn(name = "IDPrimke", referencedColumnName = "IDPrimke")
    private PrimkaEntity stavkaPrimkePrimka;

    @ManyToOne
    @JoinColumn(name = "IDRobe", referencedColumnName = "IDRobe")
    private RobaEntity stavkaPrimkeRobe;

    @Basic
    @Column(name = "Kolicina")
    private Integer kolicina;

    @Basic
    @Column(name = "Storno")
    private Boolean storno;

    @Basic
    @Column(name = "DatumStorno")
    private LocalDate datumStorno;
}
