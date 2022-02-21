package hr.datastock.controllers;

import hr.datastock.DatastockJavaFXAplication.StageReadyEvent;
import hr.datastock.services.StageInitializerService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainController {

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

    @Autowired
    private StageInitializerService stageInitializerService;

    public void screenFirme() throws IOException {
        this.stageInitializerService.onStartOfFirme();
    }

    public void screenRoba() throws IOException {
        this.stageInitializerService.onStartOfRoba();
    }

    public void screenPrimke() throws IOException {
        this.stageInitializerService.onStartOfPrimka();
    }

    public void screenStavkaPrimke() throws IOException {
        this.stageInitializerService.onStartOfStavkaPrimka();
    }

    public void screenStornoStavkaPrimke() throws IOException {
        this.stageInitializerService.onStartOfStornoStavkaPrimka();
    }

    public void screenIzdatnice() throws IOException {
        this.stageInitializerService.onStartOfIzdatnica();
    }

    public void screenStavkaIzdatnice() throws IOException {
        this.stageInitializerService.onStartOfStavkaIzdatnica();
    }

    public void screenStornoStavkaIzdatnice() throws IOException {
        this.stageInitializerService.onStartOfStornoStavkaIzdatnica();
    }

    public void onMouseClickExit() {
        this.vBoxSideBarExit.getScene().getWindow().hide();
    }

    public void onMouseClickUserManagment() throws IOException {
        this.vBoxSideBarUserManagment.getScene().getWindow().hide();
        this.stageInitializerService.onStartOfRacun();
    }

    public void onMouseClickLogout() {
        this.vBoxSideBarLogout.getScene().getWindow().hide();
        this.stageInitializerService.onStartOfLogin(new StageReadyEvent(new Stage()));
    }

}
