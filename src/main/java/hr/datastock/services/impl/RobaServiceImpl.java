package hr.datastock.services.impl;

import hr.datastock.entities.RobaEntity;
import hr.datastock.exceptions.RobaEntityExistsRuntimeException;
import hr.datastock.repositories.RobaRepository;
import hr.datastock.services.RobaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class RobaServiceImpl implements RobaService {

    @Autowired
    private RobaRepository robaRepository;

    @Override
    public List<RobaEntity> getAll() {
        return robaRepository.findAll();
    }

    @Override
    public Long getById(Long id){
        if (Objects.nonNull(id)){
            Optional<RobaEntity> firma = robaRepository.findById(id);
            if (firma.isPresent()){
                return firma.get().getIdRobe();
            }
        }
        return null;
    }

    @Override
    public RobaEntity createRoba(RobaEntity roba) {
        return robaRepository.save(roba);
    }

    @Override
    public RobaEntity updateRoba(RobaEntity roba, Long id) {
        return robaRepository.findById(id)
                .map(existingRoba -> {
                    existingRoba.setNazivArtikla(roba.getNazivArtikla());
                    existingRoba.setKolicina(roba.getKolicina());
                    existingRoba.setCijena(roba.getCijena());
                    existingRoba.setOpis(roba.getOpis());
                    existingRoba.setJmj(roba.getJmj());
                    return robaRepository.saveAndFlush(existingRoba);
                }).orElseThrow(() -> new RobaEntityExistsRuntimeException(id));
    }

    @Override
    public void deleteRoba(Long id) {
        robaRepository.deleteById(id);
    }

}
