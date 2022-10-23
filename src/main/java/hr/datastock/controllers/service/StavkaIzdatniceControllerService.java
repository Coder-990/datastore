package hr.datastock.controllers.service;

import hr.datastock.controllers.StavkaIzdatniceController;
import hr.datastock.entities.StavkaIzdatniceEntity;

import java.util.Optional;

public interface StavkaIzdatniceControllerService {

    void init(final StavkaIzdatniceController sic);

    void searchData(final StavkaIzdatniceController sic);

    StavkaIzdatniceEntity saveStavkaIzdatnice(final StavkaIzdatniceController sic);

    Optional<StavkaIzdatniceEntity> stornoStavkaIzdatnice(final StavkaIzdatniceController sic);

    void clearRecords(final StavkaIzdatniceController sic);
}
