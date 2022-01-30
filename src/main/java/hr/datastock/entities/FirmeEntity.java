package hr.datastock.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "firme", schema = "datastock")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FirmeEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idFirme;
    private String oibFirme;
    private String nazivFirme;

    @Id
    @Column(name = "IDFirme")
    public Long getIdFirme() {
        return idFirme;
    }
    public void setIdFirme(Long idFirme) {
        this.idFirme = idFirme;
    }

    @Basic
    @Column(name = "OIBFirme")
    public String getOibFirme() {
        return oibFirme;
    }
    public void setOibFirme(String oibFirme) {
        this.oibFirme = oibFirme;
    }

    @Basic
    @Column(name = "NazivFirme")
    public String getNazivFirme() {
        return nazivFirme;
    }
    public void setNazivFirme(String nazivFirme) {
        this.nazivFirme = nazivFirme;
    }
}
