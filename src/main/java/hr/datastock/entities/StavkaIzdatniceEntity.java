package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "stavkaizdatnice", schema = "datastock")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StavkaIzdatniceEntity {

    private Long idStavkaIzdatnice;
    private Integer kolicina;
    private IzdatnicaEntity stavkaIzdatniceIzdatnica;
    private RobaEntity stavkaIzdatniceRobe;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDStavkaIzdatnice")
    public Long getIdStavkaIzdatnice() {
        return idStavkaIzdatnice;
    }
    public void setIdStavkaIzdatnice(Long idStavkaIzdatnice) {
        this.idStavkaIzdatnice = idStavkaIzdatnice;
    }

    @Basic
    @Column(name = "Kolicina")
    public Integer getKolicina() {
        return kolicina;
    }
    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    @ManyToOne
    @JoinColumn(name = "IDIzdatnice", referencedColumnName = "IDIzdatnice")
    public IzdatnicaEntity getStavkaIzdatniceIzdatnica() {
        return stavkaIzdatniceIzdatnica;
    }
    public void setStavkaIzdatniceIzdatnica(IzdatnicaEntity stavkaIzdatniceIzdatnica) {
        this.stavkaIzdatniceIzdatnica = stavkaIzdatniceIzdatnica;
    }

    @ManyToOne
    @JoinColumn(name = "IDRobe", referencedColumnName = "IDRobe")
    public RobaEntity getStavkaIzdatniceRobe() {
        return stavkaIzdatniceRobe;
    }
    public void setStavkaIzdatniceRobe(RobaEntity stavkaIzdatniceRobe) {
        this.stavkaIzdatniceRobe = stavkaIzdatniceRobe;
    }
}