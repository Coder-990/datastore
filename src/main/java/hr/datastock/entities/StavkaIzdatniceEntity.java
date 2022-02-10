package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "stavkaizdatnice", schema = "datastock")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StavkaIzdatniceEntity {

    private Long idStavkaIzdatnice;
    private IzdatnicaEntity stavkaIzdatniceIzdatnica;
    private RobaEntity stavkaIzdatniceRobe;
    private Integer kolicina;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDStavkaIzdatnice")
    public Long getIdStavkaIzdatnice() {
        return idStavkaIzdatnice;
    }

    public void setIdStavkaIzdatnice(Long idStavkaIzdatnice) {
        this.idStavkaIzdatnice = idStavkaIzdatnice;
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

    @Basic
    @Column(name = "Kolicina")
    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

}