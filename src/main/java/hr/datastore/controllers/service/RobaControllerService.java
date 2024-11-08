package hr.datastore.controllers.service;

import hr.datastore.controllers.RobaController;
import hr.datastore.entities.RobaEntity;

public interface RobaControllerService {
    void init(RobaController robaController);

    void pluckSelectedDataFromTableViewRoba(RobaController robaController);

    void searchData(RobaController robaController);

    RobaEntity saveArtikl(RobaController robaController);

    RobaEntity updateArtikl(RobaController robaController);

    void deleteArtikl(RobaController robaController);

    void clearRecords(RobaController robaController);
}
