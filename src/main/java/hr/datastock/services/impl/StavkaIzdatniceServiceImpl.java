package hr.datastock.services.impl;

import hr.datastock.entities.StavkaIzdatniceEntity;
import hr.datastock.repositories.StavkaIzdatniceRepository;
import hr.datastock.services.StavkaIzdatniceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class StavkaIzdatniceServiceImpl implements StavkaIzdatniceService {

    @Autowired
    StavkaIzdatniceRepository stavkaIzdatniceRepository;

    @Override
    public List<StavkaIzdatniceEntity> getAll() {
        return stavkaIzdatniceRepository.findAll();
    }

    @Override
    public StavkaIzdatniceEntity createStavkaIzdatnice(StavkaIzdatniceEntity izdatnica) {
        return stavkaIzdatniceRepository.save(izdatnica);
    }

    @Override
    public StavkaIzdatniceEntity stornoStavkeIzdatnice(StavkaIzdatniceEntity updateStavke) {
        return stavkaIzdatniceRepository.findById(updateStavke.getIdStavkaIzdatnice())
                .map(existingStavka -> {
                    existingStavka.setIdStavkaIzdatnice(updateStavke.getIdStavkaIzdatnice());
                    existingStavka.setStavkaIzdatniceIzdatnica(updateStavke.getStavkaIzdatniceIzdatnica());
                    existingStavka.setStavkaIzdatniceRobe(updateStavke.getStavkaIzdatniceRobe());
                    existingStavka.setKolicina(updateStavke.getKolicina());
                    existingStavka.setStorno(true);
                    existingStavka.setDatumStorno(LocalDate.now());
                    return stavkaIzdatniceRepository.saveAndFlush(existingStavka);
                }).orElse(null);
    }
}
