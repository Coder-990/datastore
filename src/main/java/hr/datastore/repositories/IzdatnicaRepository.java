package hr.datastore.repositories;

import hr.datastore.entities.IzdatnicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IzdatnicaRepository extends JpaRepository<IzdatnicaEntity, Long> {
}
