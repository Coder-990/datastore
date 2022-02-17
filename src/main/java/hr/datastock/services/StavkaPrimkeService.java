package hr.datastock.services;

import hr.datastock.entities.StavkaPrimkeEntity;

import java.util.List;

public interface StavkaPrimkeService {

    List<StavkaPrimkeEntity> getAll();

    StavkaPrimkeEntity createStavkaPrimke(StavkaPrimkeEntity primka);

    StavkaPrimkeEntity createStornoStavkePrimke(StavkaPrimkeEntity stavkaPrimke);
}
