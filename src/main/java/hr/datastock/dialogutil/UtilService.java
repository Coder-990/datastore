package hr.datastock.dialogutil;

import hr.datastock.entities.RacunEntity;

public interface UtilService {

    void getWarningAlert(String alert);

    boolean isEntityUnableToRemove();

    boolean isEntityRemoved();

    boolean isDataPickedFromTableViewAlert();

    boolean isUserRegistered(RacunEntity racun);
}
