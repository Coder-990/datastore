package hr.datastock.controllers;

import hr.datastock.DatastockJavaFXAplication.StageReadyEvent;
import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.entities.RacunEntity;
import hr.datastock.security.PasswordEncryptionService;
import hr.datastock.services.RacunService;
import hr.datastock.services.StageInitializerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class RacunController {

    @FXML
    private TextField textfieldUserID;
    @FXML
    private TextField textfieldPassword;
    @FXML
    private Button buttonCreateUser;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonClearFields;
    @FXML
    private Label labelMessageGetCredidentials;
    private final UtilService utilService;
    private final RacunService racunService;
    private final StageInitializerService stageInitializerService;
    private final PasswordEncryptionService passwordEncryptionService;

    @FXML
    public RacunEntity setButtonCreateUser() {
        final String userId = textfieldUserID.getText();
        final String password = textfieldPassword.getText();
        final String alertData = this.setInputCheckingOf(userId, password);
        RacunEntity newRacun = null;
        if (!alertData.isEmpty()) {
            this.utilService.getWarningAlert(alertData);
        } else {
            String encryptedPassword = passwordEncryptionService.createMD5(password);
            newRacun = new RacunEntity(userId, encryptedPassword);
            this.getMessage(newRacun.getUserId(), newRacun.getPassword());
            this.racunService.createAccount(newRacun);
        }
        return newRacun;
    }

    @FXML
    public void setButtonClearFields() {
        this.clearRecords();
    }

    @FXML
    public void setButtonBackToLogin() {
        this.stageInitializerService.getLoginScreen(new StageReadyEvent(new Stage()));
        this.buttonLogin.getScene().getWindow().hide();
    }

    @FXML
    public void getMessage(final String userId, final String password) {
        labelMessageGetCredidentials.setText("New user created: " +
                "Userid: " + userId + " Password: " + password);
    }

    private String setInputCheckingOf(final String userId, final String password) {
        return this.getDialogData(userId, password);
    }

    private String getDialogData(final String userId, final String password) {
        final List<String> listaProvjere = new ArrayList<>();
        if (userId.trim().isEmpty()) listaProvjere.add("UserId!");
        if (password.trim().isEmpty()) listaProvjere.add("Password");
        return String.join("\n", listaProvjere);
    }

    private void clearRecords() {
        this.textfieldUserID.clear();
        this.textfieldPassword.clear();
    }
}
