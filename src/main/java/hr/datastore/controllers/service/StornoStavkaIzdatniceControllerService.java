package hr.datastore.controllers.service;

import hr.datastore.controllers.StornoStavkaIzdatniceController;

public interface StornoStavkaIzdatniceControllerService {
    void init(StornoStavkaIzdatniceController ssic);

    void searchData(StornoStavkaIzdatniceController ssic);

    void clearRecords(StornoStavkaIzdatniceController ssic);
}
