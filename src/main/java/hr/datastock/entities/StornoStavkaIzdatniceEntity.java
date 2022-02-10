package hr.datastock.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "stornoizdatnice", schema = "datastock")
public class StornoStavkaIzdatniceEntity {

    private int idStornoIzdatnice;
    private LocalDate datum;
    private StavkaIzdatniceEntity stornoStavkaIzdatnice;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDStornoIzdatnice")
    public int getIdStornoIzdatnice() {
        return idStornoIzdatnice;
    }

    public void setIdStornoIzdatnice(int idStornoIzdatnice) {
        this.idStornoIzdatnice = idStornoIzdatnice;
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
    @JoinColumn(name = "IDStavkaIzdatnice", referencedColumnName = "IDStavkaIzdatnice")
    public StavkaIzdatniceEntity getStornoIzdatniceStavkaIzdatnice() {
        return stornoStavkaIzdatnice;
    }

    public void setStornoIzdatniceStavkaIzdatnice(StavkaIzdatniceEntity stornoStavkaIzdatnice) {
        this.stornoStavkaIzdatnice = stornoStavkaIzdatnice;
    }
}
