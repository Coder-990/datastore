package hr.datastock.services;

import hr.datastock.entities.FirmeEntity;

import java.util.List;

public interface FirmeService {

    List<FirmeEntity> getAll();

    FirmeEntity createFirma(FirmeEntity company);

    FirmeEntity updateFirma(FirmeEntity company, Long id);

    void deleteFirma(Long id);
}
