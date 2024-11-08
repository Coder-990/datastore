package hr.datastore.services.impl;

import hr.datastore.entities.StavkaIzdatniceEntity;
import hr.datastore.repositories.StavkaIzdatniceRepository;
import hr.datastore.services.StavkaIzdatniceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StavkaIzdatniceServiceImpl implements StavkaIzdatniceService {

    private final StavkaIzdatniceRepository stavkaIzdatniceRepository;
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
                    return this.stavkaIzdatniceRepository.save(existingStavka);
                });
    }
}