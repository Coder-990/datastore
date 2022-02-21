package hr.datastock.services.impl;

import hr.datastock.entities.RobaEntity;
import hr.datastock.exceptions.RobaEntityExistsRuntimeException;
import hr.datastock.repositories.RobaRepository;
import hr.datastock.services.RobaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RobaServiceImpl implements RobaService {

    @Autowired
    private RobaRepository robaRepository;

    @Override
    public List<RobaEntity> getAll() {
        return this.robaRepository.findAll();
    }

    @Override
    public RobaEntity createRoba(final RobaEntity roba) {
        return this.robaRepository.save(roba);
    }

    @Override
    public RobaEntity updateRoba(RobaEntity roba, Long id) {
        return this.robaRepository.findById(id)
                .map(existingRoba -> {
                    existingRoba.setNazivArtikla(roba.getNazivArtikla());
                    existingRoba.setKolicina(roba.getKolicina());
                    existingRoba.setCijena(roba.getCijena());
                    existingRoba.setOpis(roba.getOpis());
                    existingRoba.setJmj(roba.getJmj());
                    return this.robaRepository.saveAndFlush(existingRoba);
                }).orElseThrow(() -> new RobaEntityExistsRuntimeException(id));
    }

    @Override
    public void deleteRoba(final Long id) {
        this.robaRepository.deleteById(id);
    }

}
