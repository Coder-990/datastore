package hr.datastore.controllers;

import hr.datastore.controllers.service.LoginControllerService;
import hr.datastore.dialogutil.DialogService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginControllerService loginControllerService;
    private final DialogService dialogService;
    @Getter
    @FXML
    private TextField textFieldUserId;
    @Getter
    @FXML
    private PasswordField textFieldPassword;
    @Getter
    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegister;

    public void setButtonClose() {
        try {
            this.loginControllerService.close(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("App closed successful");
    }

    public void setButtonRegister() throws IOException {
        try {
            this.loginControllerService.register(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Redirected to registration screen successful");
    }

    public void setButtonLogin() {
        try {
            String dialogCheck = this.loginControllerService
                    .getInputDataForDialogCheck(this);
            if (dialogCheck.isEmpty()) {
                this.loginControllerService.userLogin(this);
                log.info("Login and redirection to main menu screen successful");
            } else {
                this.dialogService.getWarningAlert(dialogCheck);
            }
        } catch (RuntimeException ex) {
            this.dialogService.isCredentialsValid();
            log.error(ex.getMessage());
        }
    }
}
