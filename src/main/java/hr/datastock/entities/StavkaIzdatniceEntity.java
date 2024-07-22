package hr.datastock.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StavkaIzdatniceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idStavkaIzdatnice;

    @ManyToOne
    @JoinColumn(name = "IDIzdatnice", referencedColumnName = "IDIzdatnice")
    private IzdatnicaEntity stavkaIzdatniceIzdatnica;

    @ManyToOne
    @JoinColumn(name = "IDRobe", referencedColumnName = "IDRobe")
    private RobaEntity stavkaIzdatniceRobe;

    private Integer kolicina;
    private Boolean storno;
    private LocalDate datumStorno;
}