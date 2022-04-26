package hr.datastock.controllers.service;

import hr.datastock.controllers.RacunController;
import hr.datastock.entities.RacunEntity;

public interface RacunControllerService {
    RacunEntity saveUser(RacunController racunController);

    void clearRecords(RacunController racunController);

    void backToLogin(RacunController racunController);

    String getInputDataForDialogCheck(RacunController racunController);
}
