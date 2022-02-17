package hr.datastock.repositories;

import hr.datastock.entities.StavkaPrimkeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StavkaPrimkeRepository extends JpaRepository <StavkaPrimkeEntity, Long> {
}
