package hr.datastock.controllers.service;

import hr.datastock.controllers.StavkaIzdatniceController;
import hr.datastock.entities.StavkaIzdatniceEntity;

public interface StavkaIzdatniceControllerService {

    void init(StavkaIzdatniceController sic);

    void searchData(StavkaIzdatniceController sic);

    StavkaIzdatniceEntity saveStavkaIzdatnice(StavkaIzdatniceController sic);

    void stornoStavkaIzdatnice(StavkaIzdatniceController sic);

    void clearRecords(StavkaIzdatniceController sic);
}
