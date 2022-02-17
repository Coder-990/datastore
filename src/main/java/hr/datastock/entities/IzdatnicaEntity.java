package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "izdatnica", schema = "datastock")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class IzdatnicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDIzdatnice")
    private Long idIzdatnice;

    @Basic
    @Column(name = "Datum")
    private LocalDate datum;

    @ManyToOne
    @JoinColumn(name = "IDFirme", referencedColumnName = "IDFirme")
    private FirmeEntity izdatnicaFirme;

    @Override
    public String toString() {
        return izdatnicaFirme.getNazivFirme() + "- [created: " + datum + "]";
    }
}
