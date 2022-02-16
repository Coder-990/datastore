package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "stavkaprimke", schema = "datastock")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StavkaPrimkeEntity {

    private Long idStavkaPrimke;
    private PrimkaEntity stavkaPrimkePrimka;
    private RobaEntity stavkaPrimkeRobe;
    private Integer kolicina;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDStavkaPrimke")
    public Long getIdStavkaPrimke() {
        return idStavkaPrimke;
    }
    public void setIdStavkaPrimke(Long idStavkaPrimke) {
        this.idStavkaPrimke = idStavkaPrimke;
    }

    @ManyToOne
    @JoinColumn(name = "IDPrimke", referencedColumnName = "IDPrimke")
    public PrimkaEntity getStavkaPrimkePrimka() {
        return stavkaPrimkePrimka;
    }
    public void setStavkaPrimkePrimka(PrimkaEntity stavkaPrimkePrimka) {
        this.stavkaPrimkePrimka = stavkaPrimkePrimka;
    }

    @ManyToOne
    @JoinColumn(name = "IDRobe", referencedColumnName = "IDRobe")
    public RobaEntity getStavkaPrimkeRobe() {
        return stavkaPrimkeRobe;
    }
    public void setStavkaPrimkeRobe(RobaEntity stavkaPrimkeRobe) {
        this.stavkaPrimkeRobe = stavkaPrimkeRobe;
    }

    @Basic
    @Column(name = "Kolicina")
    public Integer getKolicina() {
        return kolicina;
    }
    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }
}
