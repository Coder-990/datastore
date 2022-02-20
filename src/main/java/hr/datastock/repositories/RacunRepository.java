package hr.datastock.repositories;

import hr.datastock.entities.RacunEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RacunRepository extends JpaRepository<RacunEntity, String> {
}
