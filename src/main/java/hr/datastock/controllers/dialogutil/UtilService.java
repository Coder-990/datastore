package hr.datastock.controllers.dialogutil;

import hr.datastock.entities.FirmeEntity;

public interface UtilService {

    FirmeEntity getWarningAlert(String alert);

    boolean isEntityUnableToRemove();

    boolean isEntityRemoved();

    boolean isDataPickedFromTableViewAlert();
}
