package hr.datastock.dialogutil;

import hr.datastock.entities.RacunEntity;

public interface DialogService {

    void getWarningAlert(String alert);

    boolean isCredentialsValid();

    boolean isEntityUnableToRemove();

    boolean isEntityRemoved();

    boolean isDataPickedFromTableViewAlert();

    boolean isUserRegistered(RacunEntity racun);
}
