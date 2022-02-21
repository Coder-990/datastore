package hr.datastock.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "firme", schema = "datastock")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FirmeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDFirme")
    private Long idFirme;

    @Basic
    @Column(name = "OIBFirme")
    private String oibFirme;

    @Basic
    @Column(name = "NazivFirme")
    private String nazivFirme;


    @Override
    public String toString() {
        return this.oibFirme + " ["+ this.nazivFirme+"]";
    }
}
