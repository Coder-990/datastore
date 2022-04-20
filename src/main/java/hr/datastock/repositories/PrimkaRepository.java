package hr.datastock.repositories;

import hr.datastock.entities.PrimkaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimkaRepository extends JpaRepository<PrimkaEntity, Long> {
}
