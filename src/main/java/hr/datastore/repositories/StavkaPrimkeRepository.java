package hr.datastore.repositories;

import hr.datastore.entities.StavkaPrimkeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StavkaPrimkeRepository extends JpaRepository <StavkaPrimkeEntity, Long> {
}
