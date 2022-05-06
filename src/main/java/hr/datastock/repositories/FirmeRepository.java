package hr.datastock.repositories;

import hr.datastock.entities.FirmeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirmeRepository extends JpaRepository<FirmeEntity, Long> {

    @Query("SELECT fe FROM FirmeEntity fe WHERE fe.oibFirme = :#{#current.oibFirme} AND  fe.idFirme <> :#{#current.idFirme}")
    List<FirmeEntity> checkIfExistingOibIsInTableFirme(@Param("current") FirmeEntity current);
}
