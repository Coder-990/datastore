package hr.datastock.dialogutil.impl;

import hr.datastock.dialogutil.DialogService;
import hr.datastock.entities.RacunEntity;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class DialogServiceImpl implements DialogService {

    @Override
    public void getWarningAlert(final String alert) {
        final Alert alertWindow = new Alert(Alert.AlertType.WARNING);
        alertWindow.setTitle("Warning");
        alertWindow.setHeaderText("Some data is missing: ");
        alertWindow.setContentText(alert);
        alertWindow.showAndWait();
    }

    @Override
    public boolean isCredentialsValid() {
        final Alert alertWindow = new Alert(Alert.AlertType.ERROR);
        alertWindow.setTitle("User credentials error");
        alertWindow.setHeaderText("Unable to login user");
        alertWindow.setContentText("You are unable to login with these credentials, enter valid credentials");
        final AtomicBoolean isUserIdValid = new AtomicBoolean(false);
        alertWindow.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
                isUserIdValid.set(true);
        });
        alertWindow.getAlertType();
        return isUserIdValid.get();
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
        alertWindow.setContentText("You are about to remove this item from table, continue?");
        final AtomicBoolean isRemoved = new AtomicBoolean(false);
        alertWindow.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
                isRemoved.set(true);
        });
        alertWindow.getAlertType();
        return isRemoved.get();
    }

    @Override
    public boolean isEntityCanceled() {
        final Alert alertWindow = new Alert(Alert.AlertType.CONFIRMATION);
        alertWindow.setTitle("Cancel item");
        alertWindow.setHeaderText("Are you sure to continue?");
        alertWindow.setContentText("You are about to cancel this item, continue?");
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

    @Override
    public boolean isUserRegistered(final RacunEntity racun) {
        final Alert alertWindow = new Alert(Alert.AlertType.INFORMATION);
        alertWindow.setTitle("User registration");
        alertWindow.setHeaderText("User successfully created");
        alertWindow.setContentText("User password of UserID: '" + racun.getUserId() + "' was successfully encrypted" +
                "\nEncrypted password: " + racun.getPassword());
        final AtomicBoolean isCreated = new AtomicBoolean(false);
        alertWindow.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
                isCreated.set(true);
        });
        alertWindow.getAlertType();
        return isCreated.get();
    }
}
