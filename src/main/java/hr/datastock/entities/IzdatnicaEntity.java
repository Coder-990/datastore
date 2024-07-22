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
public class IzdatnicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idIzdatnice;
    private LocalDate datum;

    @ManyToOne
    @JoinColumn(name = "IDFirme", referencedColumnName = "IDFirme")
    private FirmeEntity izdatnicaFirme;

    @Override
    public String toString() {
        return this.izdatnicaFirme.getNazivFirme() + "- [created: " + this.datum + "]";
    }
}
