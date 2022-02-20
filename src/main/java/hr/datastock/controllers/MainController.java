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
        stageInitializerService.onStartOfFirme();
    }

    public void screenRoba() throws IOException {
        stageInitializerService.onStartOfRoba();
    }

    public void screenIzdatnice() throws IOException {
        stageInitializerService.onStartOfIzdatnica();
    }

    public void screenStavkaIzdatnice() throws IOException {
        stageInitializerService.onStartOfStavkaIzdatnica();
    }

    public void screenStornoStavkaIzdatnice() throws IOException {
        stageInitializerService.onStartOfStornoStavkaIzdatnica();
    }

    public void screenPrimke() throws IOException {
        stageInitializerService.onStartOfPrimka();
    }

    public void screenStavkaPrimke() throws IOException {
        stageInitializerService.onStartOfStavkaPrimka();
    }

    public void screenStornoStavkaPrimke() throws IOException {
        stageInitializerService.onStartOfStornoStavkaPrimka();
    }
}
