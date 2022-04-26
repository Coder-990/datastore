package hr.datastock.controllers.service;

import hr.datastock.controllers.FirmeController;
import hr.datastock.entities.FirmeEntity;

public interface FirmeControllerService {

    void init(FirmeController firmeController);

    void pluckSelectedDataFromTableViewFirma(FirmeController firmeController);

    void searchData(FirmeController firmeController);

    FirmeEntity saveFirma(FirmeController firmeController);

    FirmeEntity updateFirma(FirmeController firmeController);

    void deleteFirma(FirmeController firmeController);

    void clearRecords(FirmeController firmeController);

    String getInputDataForDialogCheck(FirmeController firmeController);
}
