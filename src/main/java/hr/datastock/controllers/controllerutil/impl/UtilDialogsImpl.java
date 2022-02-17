package hr.datastock.controllers.controllerutil.impl;

import hr.datastock.controllers.controllerutil.UtilService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class UtilDialogsImpl implements UtilService {

    @Override
    public void getWarningAlert(String alert) {
        Alert alertWindow = new Alert(Alert.AlertType.WARNING);
        alertWindow.setTitle("Error");
        alertWindow.setHeaderText("Please input missing records: ");
        alertWindow.setContentText(alert);
        alertWindow.showAndWait();
    }

    @Override
    public void getErrorAlert(String alert) {
        Alert alertWindow = new Alert(Alert.AlertType.ERROR);
        alertWindow.setTitle("Error");
        alertWindow.setHeaderText("Please input missing records: ");
        alertWindow.setContentText(alert);
        alertWindow.showAndWait();
    }

    @Override
    public boolean getConfirmForDeleteAlert() {
        Alert alertWindow = new Alert(Alert.AlertType.CONFIRMATION);
        alertWindow.setTitle("Delete item");
        alertWindow.setHeaderText("Are you sure to delete?");
        alertWindow.setContentText("You are about to remove this item from database, Continue?");
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        alertWindow.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
               isDeleted.set(true);
        });
        alertWindow.getAlertType();
        return isDeleted.get();
    }

    @Override
    public void getnformationMessageAlert() {
        Alert alertWindow = new Alert(Alert.AlertType.INFORMATION);
        alertWindow.setTitle("Info");
        alertWindow.setHeaderText("Job done");
        alertWindow.showAndWait();
    }
}
