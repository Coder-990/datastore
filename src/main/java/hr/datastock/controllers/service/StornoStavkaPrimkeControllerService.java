package hr.datastock.controllers.service;

import hr.datastock.controllers.StornoStavkaPrimkeController;

public interface StornoStavkaPrimkeControllerService {

    void init(StornoStavkaPrimkeController sspc);

    void searchData(StornoStavkaPrimkeController sspc);

    void clearRecords(StornoStavkaPrimkeController sspc);
}
