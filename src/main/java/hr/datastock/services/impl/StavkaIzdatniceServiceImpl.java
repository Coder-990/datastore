package hr.datastock.services.impl;

import hr.datastock.entities.StavkaIzdatniceEntity;
import hr.datastock.repositories.StavkaIzdatniceRepository;
import hr.datastock.services.StavkaIzdatniceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class StavkaIzdatniceServiceImpl implements StavkaIzdatniceService {

    @Autowired
    StavkaIzdatniceRepository stavkaIzdatniceRepository;

    @Override
    public List<StavkaIzdatniceEntity> getAll() {
        return this.stavkaIzdatniceRepository.findAll();
    }

    @Override
    public StavkaIzdatniceEntity createStavkaIzdatnice(final StavkaIzdatniceEntity izdatnica) {
        return this.stavkaIzdatniceRepository.save(izdatnica);
    }

    @Override
    public Optional<StavkaIzdatniceEntity> createStornoStavkeIzdatnice(final StavkaIzdatniceEntity stornoStavke) {
        return this.stavkaIzdatniceRepository.findById(stornoStavke.getIdStavkaIzdatnice())
                .map(existingStavka -> {
                    existingStavka.setIdStavkaIzdatnice(stornoStavke.getIdStavkaIzdatnice());
                    existingStavka.setStavkaIzdatniceIzdatnica(stornoStavke.getStavkaIzdatniceIzdatnica());
                    existingStavka.setStavkaIzdatniceRobe(stornoStavke.getStavkaIzdatniceRobe());
                    existingStavka.setKolicina(stornoStavke.getKolicina());
                    existingStavka.setStorno(true);
                    existingStavka.setDatumStorno(LocalDate.now());
                    return this.stavkaIzdatniceRepository.saveAndFlush(existingStavka);
                });
    }

    @Override
    public StavkaIzdatniceEntity createEqualityBetweenAmount(final StavkaIzdatniceEntity stavkaIzdatnicaEquality) {
        return this.stavkaIzdatniceRepository.findById(stavkaIzdatnicaEquality.getIdStavkaIzdatnice())
                .map(existingStavka -> {
                    existingStavka.setIdStavkaIzdatnice(stavkaIzdatnicaEquality.getIdStavkaIzdatnice());
                    existingStavka.setStavkaIzdatniceIzdatnica(stavkaIzdatnicaEquality.getStavkaIzdatniceIzdatnica());
                    existingStavka.setStavkaIzdatniceRobe(stavkaIzdatnicaEquality.getStavkaIzdatniceRobe());
                    existingStavka.setKolicina(stavkaIzdatnicaEquality.getKolicina());
                    existingStavka.setStorno(false);
                    existingStavka.setDatumStorno(null);
                    return this.stavkaIzdatniceRepository.saveAndFlush(existingStavka);
                }).orElse(null);
    }
}