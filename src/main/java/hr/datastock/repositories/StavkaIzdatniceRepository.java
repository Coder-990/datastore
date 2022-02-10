package hr.datastock.repositories;

import hr.datastock.entities.StavkaIzdatniceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StavkaIzdatniceRepository extends JpaRepository<StavkaIzdatniceEntity, Long> {
}
