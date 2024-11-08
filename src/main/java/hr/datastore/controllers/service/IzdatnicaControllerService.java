package hr.datastore.controllers.service;

import hr.datastore.controllers.IzdatnicaController;
import hr.datastore.entities.IzdatnicaEntity;

public interface IzdatnicaControllerService {

    void init(IzdatnicaController izdatnicaController);

    void searchData(IzdatnicaController izdatnicaController);

    IzdatnicaEntity saveIzdatnica(IzdatnicaController izdatnicaController);

    void deleteIzdatnica(IzdatnicaController izdatnicaController);

    void clearRecords(IzdatnicaController izdatnicaController);
}
