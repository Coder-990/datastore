package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "roba", schema = "datastock")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RobaEntity {

    private Long idRobe;
    private String nazivArtikla;
    private Integer kolicina;
    private BigDecimal cijena;
    private String opis;
    private String jmj;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDRobe")
    public Long getIdRobe() {
        return idRobe;
    }

    public void setIdRobe(Long idRobe) {
        this.idRobe = idRobe;
    }

    @Basic
    @Column(name = "NazivArtikla")
    public String getNazivArtikla() {
        return nazivArtikla;
    }

    public void setNazivArtikla(String nazivArtikla) {
        this.nazivArtikla = nazivArtikla;
    }

    @Basic
    @Column(name = "Kolicina")
    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    @Basic
    @Column(name = "Cijena")
    public BigDecimal getCijena() {
        return cijena;
    }

    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    @Basic
    @Column(name = "Opis")
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Basic
    @Column(name = "Jmj")
    public String getJmj() {
        return jmj;
    }

    public void setJmj(String jmj) {
        this.jmj = jmj;
    }

    @Override
    public String toString() {
        return " NazivArtikla= " + nazivArtikla + ", kolicina= " + kolicina + ", cijena= " + cijena;
    }
}
