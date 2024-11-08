package hr.datastore.controllers.service;

import hr.datastore.controllers.RacunController;
import hr.datastore.entities.RacunEntity;

public interface RacunControllerService {
    RacunEntity saveUser(RacunController racunController);

    void clearRecords(RacunController racunController);

    void backToLogin(RacunController racunController);

    String getInputDataForDialogCheck(RacunController racunController);
}
