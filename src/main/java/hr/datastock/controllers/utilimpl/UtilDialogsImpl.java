package hr.datastock.controllers.utilimpl;

import hr.datastock.controllers.UtilService;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

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
    public void getConfirmForDeleteAlert(Button buttonConfirm) {
        Alert alertWindow = new Alert(Alert.AlertType.CONFIRMATION);
        alertWindow.setTitle("Delete item");
        alertWindow.setHeaderText("Are you sure to delete?");
        alertWindow.setContentText("You are about to remove this item from database, Continue?");
        alertWindow.showAndWait();
        alertWindow.getAlertType();
    }

    @Override
    public void getnformationMessageAlert() {
        Alert alertWindow = new Alert(Alert.AlertType.INFORMATION);
        alertWindow.setTitle("Info");
        alertWindow.setHeaderText("Job done");
        alertWindow.showAndWait();
    }
}
