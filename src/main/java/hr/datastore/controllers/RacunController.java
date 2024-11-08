package hr.datastore.controllers;

import hr.datastore.controllers.service.RacunControllerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RacunController {
    private final RacunControllerService racunControllerService;
    @Getter
    @FXML
    private TextField textFieldUserID;
    @Getter
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Button buttonCreateUser;
    @Getter
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonClearFields;

    public void setButtonBackToLogin() {
        try {
            this.racunControllerService.backToLogin(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Redirected to login screen successful");
    }

    public void setButtonSave() {
        try {
            if (this.racunControllerService.saveUser(this) != null)
                log.info("Racun record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonClear() {
        try {
            this.racunControllerService.clearRecords(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Racun records cleared successful");
    }

}
