package hr.datastock.controllers.service;

import hr.datastock.controllers.RobaController;
import hr.datastock.entities.RobaEntity;

public interface RobaControllerService {
    void init(RobaController robaController);

    void pluckSelectedDataFromTableViewRoba(RobaController robaController);

    void searchData(RobaController robaController);

    RobaEntity saveArtikl(RobaController robaController);

    RobaEntity updateArtikl(RobaController robaController);

    void deleteArtikl(RobaController robaController);

    void clearRecords(RobaController robaController);
}
