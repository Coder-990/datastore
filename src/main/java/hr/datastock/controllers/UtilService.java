package hr.datastock.controllers;

import javafx.scene.control.Button;

public interface UtilService {

    void getWarningAlert(String alert);

    void getErrorAlert(String alert);

    void getConfirmForDeleteAlert(Button buttonConfirm);

    void getnformationMessageAlert();
}
