package hr.datastock.controllers.service;

import hr.datastock.controllers.StavkaPrimkeController;
import hr.datastock.entities.StavkaPrimkeEntity;

public interface StavkaPrimkeControllerService {
    void init(StavkaPrimkeController spc);

    void searchData(StavkaPrimkeController spc);

    StavkaPrimkeEntity saveStavkaPrimke(StavkaPrimkeController spc);

    void stornoStavkaPrimke(StavkaPrimkeController spc);

    void clearRecords(StavkaPrimkeController spc);
}
