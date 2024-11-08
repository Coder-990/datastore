package hr.datastore.controllers.service;

import hr.datastore.controllers.StornoStavkaPrimkeController;

public interface StornoStavkaPrimkeControllerService {

    void init(StornoStavkaPrimkeController sspc);

    void searchData(StornoStavkaPrimkeController sspc);

    void clearRecords(StornoStavkaPrimkeController sspc);
}
