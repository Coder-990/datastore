package hr.datastock.controllers;

import hr.datastock.DatastockJavaFXAplication.StageReadyEvent;
import hr.datastock.security.PasswordEncryptionService;
import hr.datastock.services.RacunService;
import hr.datastock.services.StageInitializerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginController {

    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegister;
    @FXML
    private Label labelMessage;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private TextField textFieldUserId;
    @Autowired
    StageInitializerService stageInitializerService;
    @Autowired
    RacunService racunService;
    @Autowired
    PasswordEncryptionService passwordEncryptionService;

    @FXML
    void setButtonClose() {
        this.buttonClose.getScene().getWindow().hide();
    }

    @FXML
    void setButtonRegister() throws IOException {
        this.stageInitializerService.getRacunScreen();
        this.setButtonClose();
    }

    @FXML
    void setButtonLogin() {
        try {
            this.racunService.login(this.textFieldUserId.getText(),
                    passwordEncryptionService.createMD5(this.textFieldPassword.getText()));
            this.stageInitializerService.getMainMenuScreen(new StageReadyEvent(new Stage()));
            this.setButtonClose();
        } catch (RuntimeException ex) {
            labelMessage.setText(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
