package hr.datastore.services.impl;

import hr.datastore.entities.FirmeEntity;
import hr.datastore.exceptions.FirmeEntityExistsRuntimeException;
import hr.datastore.exceptions.FirmeEntityNotFoundRuntimeException;
import hr.datastore.repositories.FirmeRepository;
import hr.datastore.services.FirmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FirmeServiceImpl implements FirmeService {

    private final FirmeRepository firmeRepository;

    @Override
    public List<FirmeEntity> getAll() {
        return this.firmeRepository.findAll();
    }

    @Override
    public FirmeEntity getOneById(final Long id) {
        return this.firmeRepository.findById(id).orElseThrow(() -> new FirmeEntityNotFoundRuntimeException(id));
    }

    @Override
    public FirmeEntity createFirma(final FirmeEntity firma) {
        return this.saveFirma(firma);
    }

    @Override
    public FirmeEntity updateExistingFirma(final FirmeEntity newFirmaValue, final Long id) {
        return Optional.of(this.getOneById(id))
                .map(existingFirma -> {
                    existingFirma.setOibFirme(newFirmaValue.getOibFirme());
                    existingFirma.setNazivFirme(newFirmaValue.getNazivFirme());
                    return this.saveFirma(existingFirma);
                }).orElseThrow(() -> new FirmeEntityExistsRuntimeException(newFirmaValue));
    }

    @Override
    public void deleteFirma(Long id) {
        this.firmeRepository.deleteById(id);
    }

    private FirmeEntity saveFirma(FirmeEntity firmeEntity) {
        if (firmeEntity.getIdFirme() == null) {
            throw new FirmeEntityNotFoundRuntimeException(firmeEntity.getIdFirme());
        } else if (firmeEntity.getIdFirme() != null || firmeEntity.getOibFirme().equals(
                getAll().stream().map(FirmeEntity::getOibFirme).collect(Collectors.joining()))) {
            List<FirmeEntity> firmeOibOverlap = this.firmeRepository.checkIfExistingOibIsInTableFirme(firmeEntity);
            if (!firmeOibOverlap.isEmpty()) {
                throw new FirmeEntityExistsRuntimeException(firmeOibOverlap.get(0));
            }
        }
        return firmeRepository.save(firmeEntity);
    }

}
