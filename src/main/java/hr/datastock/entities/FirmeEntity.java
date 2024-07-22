package hr.datastock.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FirmeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idFirme;
    private String oibFirme;
    private String nazivFirme;

    @Override
    public String toString() {
        return this.oibFirme + " [" + this.nazivFirme + "]";
    }
}
