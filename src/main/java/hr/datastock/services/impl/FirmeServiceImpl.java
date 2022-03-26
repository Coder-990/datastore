package hr.datastock.services.impl;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.exceptions.FirmeEntityExistsRuntimeException;
import hr.datastock.repositories.FirmeRepository;
import hr.datastock.services.FirmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FirmeServiceImpl implements FirmeService {

    private final FirmeRepository firmeRepository;

    @Override
    public List<FirmeEntity> getAll() {
        return this.firmeRepository.findAll();
    }

    @Override
    public FirmeEntity createFirma(final FirmeEntity firma) {
        return this.firmeRepository.save(firma);
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
