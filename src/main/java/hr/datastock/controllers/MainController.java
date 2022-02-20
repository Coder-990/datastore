package hr.datastock.controllers;

import hr.datastock.StageInitializer;
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
    private StageInitializer stageInitializer;

    public void screenFirme() throws IOException {
        stageInitializer.onStartOfFirme();
    }

    public void screenRoba() throws IOException {
        stageInitializer.onStartOfRoba();
    }

    public void screenIzdatnice() throws IOException {
        stageInitializer.onStartOfIzdatnica();
    }

    public void screenStavkaIzdatnice() throws IOException {
        stageInitializer.onStartOfStavkaIzdatnica();
    }

    public void screenStornoStavkaIzdatnice() throws IOException {
        stageInitializer.onStartOfStornoStavkaIzdatnica();
    }

    public void screenPrimke() throws IOException {
        stageInitializer.onStartOfPrimka();
    }

    public void screenStavkaPrimke() throws IOException {
        stageInitializer.onStartOfStavkaPrimka();
    }

    public void screenStornoStavkaPrimke() throws IOException {
        stageInitializer.onStartOfStornoStavkaPrimka();
    }
}
