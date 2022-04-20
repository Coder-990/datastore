package hr.datastock.services.impl;

import hr.datastock.entities.RobaEntity;
import hr.datastock.exceptions.RobaEntityExistsRuntimeException;
import hr.datastock.repositories.RobaRepository;
import hr.datastock.services.RobaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RobaServiceImpl implements RobaService {

    private final RobaRepository robaRepository;
    @Override
    public List<RobaEntity> getAll() {
        return this.robaRepository.findAll();
    }
    @Override
    public Optional<RobaEntity> getOneById(final Long id) {
        return this.robaRepository.findById(id);
    }
    @Override
    public RobaEntity createRoba(final RobaEntity roba) {
        return this.robaRepository.save(roba);
    }
    @Override
    public RobaEntity updateRoba(final RobaEntity roba, final Long id) {
        return this.getOneById(id)
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
