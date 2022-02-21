package hr.datastock.controllers.controllerutil.impl;

import hr.datastock.controllers.controllerutil.UtilService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class UtilDialogsServiceImpl implements UtilService {

    @Override
    public void getWarningAlert(final String alert) {
        final Alert alertWindow = new Alert(Alert.AlertType.WARNING);
        alertWindow.setTitle("Warning");
        alertWindow.setHeaderText("Some data is mising: ");
        alertWindow.setContentText(alert);
        alertWindow.showAndWait();
    }

    @Override
    public void getErrorAlert(final String alert) {
        final Alert alertWindow = new Alert(Alert.AlertType.ERROR);
        alertWindow.setTitle("Error");
        alertWindow.setHeaderText("Please input missing records: ");
        alertWindow.setContentText(alert);
        alertWindow.showAndWait();
    }

    @Override
    public boolean getConfirmForRemoveAlert() {
        final Alert alertWindow = new Alert(Alert.AlertType.CONFIRMATION);
        alertWindow.setTitle("Delete item");
        alertWindow.setHeaderText("Are you sure to continue?");
        alertWindow.setContentText("You are about to remove this item from table, Continue?");
        final AtomicBoolean isRemoved = new AtomicBoolean(false);
        alertWindow.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
               isRemoved.set(true);
        });
        alertWindow.getAlertType();
        return isRemoved.get();
    }

    @Override
    public void getIformationMessageAlert() {
        final Alert alertWindow = new Alert(Alert.AlertType.INFORMATION);
        alertWindow.setTitle("Info");
        alertWindow.setHeaderText("Job done");
        alertWindow.showAndWait();
    }
}
