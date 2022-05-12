package hr.datastock.controllers.service;

import hr.datastock.controllers.StavkaPrimkeController;
import hr.datastock.entities.StavkaPrimkeEntity;

import java.util.Optional;

public interface StavkaPrimkeControllerService {
    void init(StavkaPrimkeController spc);

    void searchData(StavkaPrimkeController spc);

    StavkaPrimkeEntity saveStavkaPrimke(StavkaPrimkeController spc);

    Optional<StavkaPrimkeEntity> stornoStavkaPrimke(StavkaPrimkeController spc);

    void clearRecords(StavkaPrimkeController spc);
}
