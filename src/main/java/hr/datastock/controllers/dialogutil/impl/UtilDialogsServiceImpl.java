package hr.datastock.controllers.dialogutil.impl;

import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.entities.FirmeEntity;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class UtilDialogsServiceImpl implements UtilService {

    @Override
    public FirmeEntity getWarningAlert(final String alert) {
        final Alert alertWindow = new Alert(Alert.AlertType.WARNING);
        alertWindow.setTitle("Warning");
        alertWindow.setHeaderText("Some data is mising: ");
        alertWindow.setContentText(alert);
        alertWindow.showAndWait();
        return null;
    }

    @Override
    public boolean isEntityUnableToRemove() {
        final Alert alertWindow = new Alert(Alert.AlertType.ERROR);
        alertWindow.setTitle("Error");
        alertWindow.setHeaderText("Unable to delete attached data.");
        alertWindow.setContentText("You are unable to delete selected data, entity ID is attached by other entity ID");
        final AtomicBoolean isEntityIdAttached = new AtomicBoolean(false);
        alertWindow.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
                isEntityIdAttached.set(true);
        });
        alertWindow.getAlertType();
        return isEntityIdAttached.get();
    }

    @Override
    public boolean isEntityRemoved() {
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
    public boolean isDataPickedFromTableViewAlert() {
        final Alert alertWindow = new Alert(Alert.AlertType.INFORMATION);
        alertWindow.setTitle("Info");
        alertWindow.setHeaderText("None data is picked, pick some data");
        final AtomicBoolean isPicked = new AtomicBoolean(false);
        alertWindow.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
                isPicked.set(true);
        });
        alertWindow.getAlertType();
        return isPicked.get();
    }
}
