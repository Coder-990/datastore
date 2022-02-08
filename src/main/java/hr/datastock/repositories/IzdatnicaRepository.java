package hr.datastock.repositories;

import hr.datastock.entities.IzdatnicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IzdatnicaRepository extends JpaRepository<IzdatnicaEntity, Long> {
}
