package hr.datastock.controllers.controllerutil;

public interface UtilService {

    void getWarningAlert(String alert);

    void getErrorAlert(String alert);

    boolean getConfirmForDeleteAlert();

    void getnformationMessageAlert();
}
