package hr.datastock.controllers.service;

import hr.datastock.controllers.StavkaIzdatniceController;
import hr.datastock.entities.StavkaIzdatniceEntity;

import java.util.Optional;

public interface StavkaIzdatniceControllerService {

    void init(StavkaIzdatniceController sic);

    void searchData(StavkaIzdatniceController sic);

    StavkaIzdatniceEntity saveStavkaIzdatnice(StavkaIzdatniceController sic);

    Optional<StavkaIzdatniceEntity> stornoStavkaIzdatnice(StavkaIzdatniceController sic);

    void clearRecords(StavkaIzdatniceController sic);
}
