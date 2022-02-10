package hr.datastock.services;

import hr.datastock.entities.RobaEntity;

import java.util.List;

public interface RobaService {
    List<RobaEntity> getAll();

    Long getById(Long id);

    RobaEntity createRoba(RobaEntity roba);

    RobaEntity updateRoba(RobaEntity roba, Long id);

    void deleteRoba(Long id);
}
