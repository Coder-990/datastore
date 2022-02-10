package hr.datastock.repositories;

import hr.datastock.entities.StornoStavkaIzdatniceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StornoStavkaIzdatniceRepository extends JpaRepository<StornoStavkaIzdatniceEntity, Long> {
}
