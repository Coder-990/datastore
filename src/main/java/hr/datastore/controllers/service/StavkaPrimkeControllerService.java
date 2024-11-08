package hr.datastore.controllers.service;

import hr.datastore.controllers.StavkaPrimkeController;
import hr.datastore.entities.StavkaPrimkeEntity;

import java.util.Optional;

public interface StavkaPrimkeControllerService {
    void init(StavkaPrimkeController spc);

    void searchData(StavkaPrimkeController spc);

    StavkaPrimkeEntity saveStavkaPrimke(StavkaPrimkeController spc);

    Optional<StavkaPrimkeEntity> stornoStavkaPrimke(StavkaPrimkeController spc);

    void clearRecords(StavkaPrimkeController spc);
}
