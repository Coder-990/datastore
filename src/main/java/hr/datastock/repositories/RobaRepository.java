package hr.datastock.repositories;

import hr.datastock.entities.RobaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RobaRepository extends JpaRepository<RobaEntity, Long> {

    @Query("SELECT re" +
            " FROM RobaEntity re " +
            "WHERE re.nazivArtikla = :#{#current.nazivArtikla} " +
            "AND  re.idRobe <> :#{#current.idRobe}")
    List<RobaEntity> checkIfExistingArticleNameIsInTableRoba(@Param("current") RobaEntity current);
}
