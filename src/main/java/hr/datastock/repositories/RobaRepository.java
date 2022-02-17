package hr.datastock.repositories;

import hr.datastock.entities.RobaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RobaRepository extends JpaRepository<RobaEntity, Long> {
}
