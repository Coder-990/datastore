package hr.datastock.controllers;

import hr.datastock.DatastockJavaFXAplication.StageReadyEvent;
import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.controllers.service.RacunControllerService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.RacunEntity;
import hr.datastock.security.PasswordEncryptionService;
import hr.datastock.services.RacunService;
import hr.datastock.services.StageInitializerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RacunController {
    private final RacunControllerService racunControllerService;
    private final UtilService utilService;
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

    public RacunEntity setButtonSave() {
        RacunEntity user = null;
        try {
            user = this.racunControllerService.saveUser(this);
            if (user != null) log.info("Racun record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        return user;
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
