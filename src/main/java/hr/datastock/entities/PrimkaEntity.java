package hr.datastock.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PrimkaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPrimke;
    private LocalDate datum;

    @ManyToOne
    @JoinColumn(name = "IDFirme", referencedColumnName = "IDFirme")
    private FirmeEntity primkaFirme;

    @Override
    public String toString() {
        return this.primkaFirme.getNazivFirme() + "-[" + this.datum + "]";
    }
}
