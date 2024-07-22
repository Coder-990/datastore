package hr.datastock.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RobaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRobe;
    private String nazivArtikla;
    private Integer kolicina;
    private BigDecimal cijena;
    private String opis;
    private String jmj;

    @Override
    public String toString() {
        return this.nazivArtikla + ", kolicina= "
                + this.kolicina + ", cijena= " + this.cijena;
    }
}
