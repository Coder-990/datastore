package hr.datastore.repositories;

import hr.datastore.entities.StavkaIzdatniceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StavkaIzdatniceRepository extends JpaRepository<StavkaIzdatniceEntity, Long> {
}
