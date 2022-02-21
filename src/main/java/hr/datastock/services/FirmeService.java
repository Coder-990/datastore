package hr.datastock.services;

import hr.datastock.entities.FirmeEntity;

import java.util.List;

public interface FirmeService {

    List<FirmeEntity> getAll();

    void createFirma(final FirmeEntity company);

    FirmeEntity updateFirma(final FirmeEntity company, final Long id);

    void deleteFirma(final Long id);
}
