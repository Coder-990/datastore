package hr.datastock.services;

import hr.datastock.entities.RobaEntity;

import java.util.List;
import java.util.Optional;

public interface RobaService {
    List<RobaEntity> getAll();

    Optional<RobaEntity> getOneById(Long id);

    RobaEntity createRoba(final RobaEntity roba);

    RobaEntity updateRoba(final RobaEntity roba, final Long id);

    void deleteRoba(Long id);
}
