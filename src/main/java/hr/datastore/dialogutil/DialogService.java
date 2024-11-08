package hr.datastore.dialogutil;

import hr.datastore.entities.RacunEntity;

public interface DialogService {

    void getWarningAlert(final String alert);

    boolean isCredentialsValid();

    boolean isEntityUnableToRemove();

    boolean isEntityRemoved();

    boolean isEntityCanceled();

    boolean isDataPickedFromTableViewAlert();

    boolean isUserRegistered(final RacunEntity racun);
}
