package hr.datastore.services;

import hr.datastore.entities.FirmeEntity;

import java.util.List;

public interface FirmeService {

    List<FirmeEntity> getAll();

    FirmeEntity getOneById(Long id);

    FirmeEntity createFirma(final FirmeEntity company);

    FirmeEntity updateExistingFirma(final FirmeEntity company, final Long id);

    void deleteFirma(final Long id);
}
