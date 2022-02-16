package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "primka", schema = "datastock")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PrimkaEntity {

    private Long idPrimke;
    private LocalDate datum;
    private FirmeEntity primkaFirme;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDPrimke")
    public Long getIdPrimke() {
        return idPrimke;
    }
    public void setIdPrimke(Long idPrimke) {
        this.idPrimke = idPrimke;
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
    public FirmeEntity getPrimkaFirme() {
        return primkaFirme;
    }
    public void setPrimkaFirme(FirmeEntity primkaFirme) {
        this.primkaFirme = primkaFirme;
    }

    @Override
    public String toString() {
        return primkaFirme.getNazivFirme() + "-[" + datum + "]";
    }
}
