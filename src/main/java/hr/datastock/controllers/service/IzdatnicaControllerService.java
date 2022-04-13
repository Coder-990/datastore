package hr.datastock.controllers.service;

import hr.datastock.controllers.IzdatnicaController;
import hr.datastock.entities.IzdatnicaEntity;

public interface IzdatnicaControllerService {

    void init(IzdatnicaController izdatnicaController);

    void searchData(IzdatnicaController izdatnicaController);

    IzdatnicaEntity saveIzdatnica(IzdatnicaController izdatnicaController);

    void deleteIzdatnica(IzdatnicaController izdatnicaController);

    void clearRecords(IzdatnicaController izdatnicaController);
}
