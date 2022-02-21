package hr.datastock.services.impl;

import hr.datastock.entities.StavkaPrimkeEntity;
import hr.datastock.repositories.StavkaPrimkeRepository;
import hr.datastock.services.StavkaPrimkeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class StavkaPrimkeServiceImpl implements StavkaPrimkeService {

    @Autowired
    StavkaPrimkeRepository stavkaPrimkeRepository;

    @Override
    public List<StavkaPrimkeEntity> getAll() {
        return this.stavkaPrimkeRepository.findAll();
    }

    @Override
    public StavkaPrimkeEntity createStavkaPrimke(final StavkaPrimkeEntity primka) {
        return this.stavkaPrimkeRepository.save(primka);
    }

    @Override
    public Optional<StavkaPrimkeEntity> createStornoStavkePrimke(final StavkaPrimkeEntity stornoStavke) {
        return this.stavkaPrimkeRepository.findById(stornoStavke.getIdStavkaPrimke())
                .map(existingStavka -> {
                    existingStavka.setIdStavkaPrimke(stornoStavke.getIdStavkaPrimke());
                    existingStavka.setStavkaPrimkePrimka(stornoStavke.getStavkaPrimkePrimka());
                    existingStavka.setStavkaPrimkeRobe(stornoStavke.getStavkaPrimkeRobe());
                    existingStavka.setKolicina(stornoStavke.getKolicina());
                    existingStavka.setStorno(true);
                    existingStavka.setDatumStorno(LocalDate.now());
                    return this.stavkaPrimkeRepository.saveAndFlush(existingStavka);
                });
    }

}
