package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "primka", schema = "datastock")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PrimkaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDPrimke")
    private Long idPrimke;

    @Basic
    @Column(name = "Datum")
    private LocalDate datum;

    @ManyToOne
    @JoinColumn(name = "IDFirme", referencedColumnName = "IDFirme")
    private FirmeEntity primkaFirme;

    @Override
    public String toString() {
        return primkaFirme.getNazivFirme() + "-[" + datum + "]";
    }
}
