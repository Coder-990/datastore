package hr.datastock.controllers;

import hr.datastock.DatastockJavaFXAplication.StageReadyEvent;
import hr.datastock.services.StageInitializerService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final StageInitializerService stageInitializerService;

    @FXML
    private VBox vBoxSideBarFirme;
    @FXML
    private VBox vBoxSideBarRoba;
    @FXML
    private VBox vBoxSideBarPrimke;
    @FXML
    private VBox vBoxSideBarStavkaPrimke;
    @FXML
    private VBox vBoxSideBarStornoStavkaPrimke;
    @FXML
    private VBox vBoxSideBarIzdatnice;
    @FXML
    private VBox vBoxSideBarStavkaIzdatnice;
    @FXML
    private VBox vBoxSideBarStornoStavkaIzdatnice;
    @FXML
    private VBox vBoxSideBarExit;
    @FXML
    private VBox vBoxSideBarLogout;
    @FXML
    private VBox vBoxSideBarUserManagment;


    public void setScreenFirme() throws IOException {
        this.stageInitializerService.getFirmeScreen();
    }

    public void setScreenRoba() throws IOException {
        this.stageInitializerService.getRobaScreen();
    }

    public void setScreenPrimke() throws IOException {
        this.stageInitializerService.getPrimkaScreen();
    }

    public void setScreenStavkaPrimke() throws IOException {
        this.stageInitializerService.getStavkaPrimkaScreen();
    }

    public void setScreenStornoStavkaPrimke() throws IOException {
        this.stageInitializerService.getStornoStavkaPrimkaScreen();
    }

    public void setScreenIzdatnice() throws IOException {
        this.stageInitializerService.getIzdatnicaScreen();
    }

    public void setScreenStavkaIzdatnice() throws IOException {
        this.stageInitializerService.getStavkaIzdatnicaScreen();
    }

    public void setScreenStornoStavkaIzdatnice() throws IOException {
        this.stageInitializerService.getStornoStavkaIzdatnicaScreen();
    }

    public void setExitApplicationOnMouseClick() {
        this.vBoxSideBarExit.getScene().getWindow().hide();
    }

    public void setRegistrationScreenOnMouseClick() throws IOException {
        this.vBoxSideBarUserManagment.getScene().getWindow().hide();
        this.stageInitializerService.getRacunScreen();
    }

    public void setLogoutScreenOnMouseClick() {
        this.vBoxSideBarLogout.getScene().getWindow().hide();
        this.stageInitializerService.getLoginScreen(new StageReadyEvent(new Stage()));
    }

}
