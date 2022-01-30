package hr.datastock.repositories;

import hr.datastock.entities.FirmeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirmeRepository extends JpaRepository<FirmeEntity, Long> {
}
