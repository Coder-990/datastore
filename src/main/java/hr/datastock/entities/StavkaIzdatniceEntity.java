package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "stavkaizdatnice", schema = "datastock")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StavkaIzdatniceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDStavkaIzdatnice")
    private Long idStavkaIzdatnice;

    @ManyToOne
    @JoinColumn(name = "IDIzdatnice", referencedColumnName = "IDIzdatnice")
    private IzdatnicaEntity stavkaIzdatniceIzdatnica;

    @ManyToOne
    @JoinColumn(name = "IDRobe", referencedColumnName = "IDRobe")
    private RobaEntity stavkaIzdatniceRobe;

    @Basic
    @Column(name = "Kolicina")
    private Integer kolicina;

    @Basic
    @Column(name = "Storno")
    private Boolean storno;

    @Basic
    @Column(name = "DatumStorno")
    private LocalDate datumStorno;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "IDStavkaIzdatnice")
//    public Long getIdStavkaIzdatnice() {
//        return idStavkaIzdatnice;
//    }
//    public void setIdStavkaIzdatnice(Long idStavkaIzdatnice) {
//        this.idStavkaIzdatnice = idStavkaIzdatnice;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "IDIzdatnice", referencedColumnName = "IDIzdatnice")
//    public IzdatnicaEntity getStavkaIzdatniceIzdatnica() {
//        return stavkaIzdatniceIzdatnica;
//    }
//    public void setStavkaIzdatniceIzdatnica(IzdatnicaEntity stavkaIzdatniceIzdatnica) {
//        this.stavkaIzdatniceIzdatnica = stavkaIzdatniceIzdatnica;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "IDRobe", referencedColumnName = "IDRobe")
//    public RobaEntity getStavkaIzdatniceRobe() {
//        return stavkaIzdatniceRobe;
//    }
//    public void setStavkaIzdatniceRobe(RobaEntity stavkaIzdatniceRobe) {
//        this.stavkaIzdatniceRobe = stavkaIzdatniceRobe;
//    }
//
//    @Basic
//    @Column(name = "Kolicina")
//    public Integer getKolicina() {
//        return kolicina;
//    }
//    public void setKolicina(Integer kolicina) {
//        this.kolicina = kolicina;
//    }

}