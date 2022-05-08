package hr.datastock.services;

import hr.datastock.entities.FirmeEntity;

import java.util.List;
import java.util.Optional;

public interface FirmeService {

    List<FirmeEntity> getAll();

    Optional<FirmeEntity> getOneById(Long id);

    FirmeEntity createFirma(final FirmeEntity company);

    FirmeEntity updateExistingFirma(final FirmeEntity company, final Long id);

    void deleteFirma(final Long id);
}
