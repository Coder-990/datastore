package hr.datastock.services;

import hr.datastock.entities.FirmeEntity;

import java.util.List;

public interface FirmeService {

    List<FirmeEntity> getAll();

    void createNew(final FirmeEntity company);

    void updateExisting(final FirmeEntity company, final Long id);

    void deleteFirma(final Long id);
}
