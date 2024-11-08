package hr.datastore.controllers.service;

import hr.datastore.controllers.PrimkaController;
import hr.datastore.entities.PrimkaEntity;

public interface PrimkaControllerService {

    void init(PrimkaController primkaController);

    void searchData(PrimkaController primkaController);

    PrimkaEntity savePrimka(PrimkaController primkaController);

    void deletePrimka(PrimkaController primkaController);

    void clearRecords(PrimkaController primkaController);
}
