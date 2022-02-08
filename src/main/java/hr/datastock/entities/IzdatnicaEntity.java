package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private Long idIzdatnice;
    private LocalDate datum;
    private FirmeEntity izdatnicaFirme;

    @Id
    @Column(name = "IDIzdatnice")
    public Long getIdIzdatnice() {
        return idIzdatnice;
    }
    public void setIdIzdatnice(Long idIzdatnice) {
        this.idIzdatnice = idIzdatnice;
    }

    @Basic
    @Column(name = "Datum")
    public LocalDate getDatum() {
        return datum;
    }
    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    @ManyToOne
    @JoinColumn(name = "IDFirme", referencedColumnName = "IDFirme")
    public FirmeEntity getIzdatnicaFirme() {
        return izdatnicaFirme;
    }
    public void setIzdatnicaFirme(FirmeEntity izdatnicaFirme) {
        this.izdatnicaFirme = izdatnicaFirme;
    }
}
