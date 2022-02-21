package hr.datastock.services;

import hr.datastock.entities.RobaEntity;

import java.util.List;

public interface RobaService {
    List<RobaEntity> getAll();

    RobaEntity createRoba(final RobaEntity roba);

    RobaEntity updateRoba(final RobaEntity roba, final Long id);

    void deleteRoba(Long id);
}
