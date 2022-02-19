package hr.datastock.controllers;

import hr.datastock.StageInitializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

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

    public static void getMainScreen() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader
                    .load(Objects.requireNonNull(
                            MainController.class.getClassLoader().getResource("view/MainView.fxml")));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void screenFirme() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainController.class.getClassLoader().getResource("view/FirmeView.fxml")));

    }

    @FXML
    public void screenPrimke() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader
                .load(Objects.requireNonNull(MainController.class
                        .getClassLoader()
                        .getResource("view/PrimkaView.fxml")));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void screenIzdatnice() throws IOException {
        BorderPane root = FXMLLoader
                .load(Objects.requireNonNull(StageInitializer.class
                        .getClassLoader()
                        .getResource("view/IzdatnicaView.fxml")));

    }

    public void screenRoba() throws IOException {
        BorderPane root = FXMLLoader
                .load(Objects.requireNonNull(StageInitializer.class
                        .getClassLoader()
                        .getResource("view/RobaView.fxml")));

    }

    public void screenStavkaIzdatnice() throws IOException {
        BorderPane root = FXMLLoader
                .load(Objects.requireNonNull(StageInitializer.class
                        .getClassLoader()
                        .getResource("view/StavkaIzdatnicaView.fxml")));

    }

    public void screenStavkaPrimke() throws IOException {
        BorderPane root = FXMLLoader
                .load(Objects.requireNonNull(StageInitializer.class
                        .getClassLoader()
                        .getResource("view/StavkaPrimkeView.fxml")));

    }

    public void screenStornoStavkaIzdatnice() throws IOException {
        BorderPane root = FXMLLoader
                .load(Objects.requireNonNull(StageInitializer.class
                        .getClassLoader()
                        .getResource("view/StornoStavkaIzdatnicaView.fxml")));

    }

    public void screenStornoStavkaPrimke() throws IOException {
        BorderPane root = FXMLLoader
                .load(Objects.requireNonNull(StageInitializer.class
                        .getClassLoader()
                        .getResource("view/StornoStavkaPrimkeView.fxml")));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
