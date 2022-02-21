package hr.datastock.controllers;

import hr.datastock.services.StageInitializerService;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainController {

    @FXML
    private MenuItem menuItemFirme;
    @FXML
    private MenuItem menuItemPrimke;
    @FXML
    private MenuItem menuItemIzdatnice;
    @FXML
    private MenuItem menuItemRoba;
    @FXML
    private MenuItem menuItemStavkaIzdatnice;
    @FXML
    private MenuItem menuItemStavkaPrimke;
    @FXML
    private MenuItem menuItemStornoStavkaPrimke;
    @FXML
    private MenuItem menuItemStornoStavkaIzdatnice;

    @Autowired
    private StageInitializerService stageInitializerService;

    public void screenFirme() throws IOException {
        this.stageInitializerService.onStartOfFirme();
    }

    public void screenRoba() throws IOException {
        this.stageInitializerService.onStartOfRoba();
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

    public void screenPrimke() throws IOException {
        this.stageInitializerService.onStartOfPrimka();
    }

    public void screenStavkaPrimke() throws IOException {
        this.stageInitializerService.onStartOfStavkaPrimka();
    }

    public void screenStornoStavkaPrimke() throws IOException {
        this.stageInitializerService.onStartOfStornoStavkaPrimka();
    }
}
