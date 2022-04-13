package hr.datastock.controllers.dialogutil;

public interface UtilService {

    void getWarningAlert(String alert);

    boolean isEntityUnableToRemove();

    boolean isEntityRemoved();

    boolean isDataPickedFromTableViewAlert();
}
