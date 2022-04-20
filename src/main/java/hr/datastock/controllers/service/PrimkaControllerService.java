package hr.datastock.controllers.service;

import hr.datastock.controllers.PrimkaController;
import hr.datastock.entities.PrimkaEntity;

public interface PrimkaControllerService {


    void init(PrimkaController primkaController);

    void searchData(PrimkaController primkaController);

    PrimkaEntity savePrimka(PrimkaController primkaController);

    void deletePrimka(PrimkaController primkaController);

    void clearRecords(PrimkaController primkaController);
}
