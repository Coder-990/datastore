package hr.datastock.repositories;

import hr.datastock.entities.FirmeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmeRepository extends JpaRepository<FirmeEntity, Long> {
}
