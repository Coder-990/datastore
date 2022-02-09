package hr.datastock.repositories;

import hr.datastock.entities.PrimkaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimkaRepository extends JpaRepository<PrimkaEntity, Long> {
}
