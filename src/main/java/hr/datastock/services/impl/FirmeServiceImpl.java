package hr.datastock.services.impl;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.exceptions.FirmeEntityExistsRuntimeException;
import hr.datastock.repositories.FirmeRepository;
import hr.datastock.services.FirmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirmeServiceImpl implements FirmeService {

    @Autowired
    private FirmeRepository firmeRepository;

    @Override
    public List<FirmeEntity> getAll() {
        return this.firmeRepository.findAll();
    }

    @Override
    public void createFirma(final FirmeEntity firma) {
        this.firmeRepository.save(firma);
    }

    @Override
    public FirmeEntity updateFirma(final FirmeEntity updateFirma, final Long id) {
        return this.firmeRepository.findById(id)
                .map(existingFirma -> {
                    existingFirma.setOibFirme(updateFirma.getOibFirme());
                    existingFirma.setNazivFirme(updateFirma.getNazivFirme());
                    return this.firmeRepository.saveAndFlush(existingFirma);
                }).orElseThrow(() -> new FirmeEntityExistsRuntimeException(id));
    }

    @Override
    public void deleteFirma(Long id) {
        this.firmeRepository.deleteById(id);
    }
}
