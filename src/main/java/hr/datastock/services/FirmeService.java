package hr.datastock.services;

import hr.datastock.entities.FirmeEntity;

import java.util.List;

public interface FirmeService {

    List<FirmeEntity> getAll();

    Long getOneByID(Long id);

    FirmeEntity createCompany(FirmeEntity company);

    FirmeEntity updateCompany(FirmeEntity company, Long id);

    void deleteCompany(Long id);
}
