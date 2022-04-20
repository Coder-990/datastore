package hr.datastock.repositories;

import hr.datastock.entities.RobaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobaRepository extends JpaRepository<RobaEntity, Long> {
}
