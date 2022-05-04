package hr.datastock.controllers.service;

import hr.datastock.controllers.StornoStavkaIzdatniceController;

public interface StornoStavkaIzdatniceControllerService {
    void init(StornoStavkaIzdatniceController ssic);

    void searchData(StornoStavkaIzdatniceController ssic);

    void clearRecords(StornoStavkaIzdatniceController ssic);
}
