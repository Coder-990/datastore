package hr.datastock.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "roba", schema = "datastock")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RobaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDRobe")
    private Long idRobe;

    @Basic
    @Column(name = "NazivArtikla")
    private String nazivArtikla;

    @Basic
    @Column(name = "Kolicina")
    private Integer kolicina;

    @Basic
    @Column(name = "Cijena")
    private BigDecimal cijena;

    @Basic
    @Column(name = "Opis")
    private String opis;

    @Basic
    @Column(name = "Jmj")
    private String jmj;

    @Override
    public String toString() {
        return this.nazivArtikla + ", kolicina= " + this.kolicina + ", cijena= " + this.cijena;
    }
}
