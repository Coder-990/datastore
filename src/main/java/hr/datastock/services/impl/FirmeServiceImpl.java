package hr.datastock.services.impl;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.exceptions.FirmeEntityExistsRuntimeException;
import hr.datastock.repositories.FirmeRepository;
import hr.datastock.services.FirmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FirmeServiceImpl implements FirmeService {

    private final FirmeRepository firmeRepository;

    @Override
    public List<FirmeEntity> getAll() {
        return this.firmeRepository.findAll();
    }

    @Override
    public Optional<FirmeEntity> getOneById(final Long id) {
        return this.firmeRepository.findById(id);
    }

    @Override
    public FirmeEntity createNew(final FirmeEntity firma) {
        return this.saveFirma(firma);
    }

    @Override
    public FirmeEntity updateExisting(final FirmeEntity newFirmaValue, final Long id) {
        return this.getOneById(id)
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

    private FirmeEntity saveFirma(FirmeEntity firmeEntity){
        if (firmeEntity.getIdFirme() != null){
            List<FirmeEntity> firmeOibOverlap = firmeRepository.checkIfExistingOibIsInTableFirme(firmeEntity);
            if (!firmeOibOverlap.isEmpty()){
                throw new FirmeEntityExistsRuntimeException(firmeOibOverlap.get(0));
            }
        }
        return firmeRepository.save(firmeEntity);
    }

}
