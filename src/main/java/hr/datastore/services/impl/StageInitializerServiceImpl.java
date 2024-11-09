package hr.datastore.services.impl;

import hr.datastore.DatastoreJavaFXApplication;
import hr.datastore.exceptions.StageInitializerRuntimeException;
import hr.datastore.services.FXMLResourceLoaderService;
import hr.datastore.services.StageInitializerService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StageInitializerServiceImpl implements StageInitializerService {

    private final String applicationTitle;
    private final ApplicationContext applicationContext;
    private final FXMLResourceLoaderService fxmlResourceLoaderService;

    private void showScreen(String viewName, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlResourceLoaderService.getResource(viewName).getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        Parent root = fxmlLoader.load();
        stage.setTitle(applicationTitle);
        stage.setScene(new Scene(root));
        stage.setResizable(true);
        stage.show();
    }

    @Override
    public void getLoginScreen(final DatastoreJavaFXApplication.StageReadyEvent stage) {
        try {
            showScreen("LoginView", stage.getStage());
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
            throw new StageInitializerRuntimeException(ex);
        }
    }

    @Override
    public void getMainMenuScreen(DatastoreJavaFXApplication.StageReadyEvent stage) {
        try {
            showScreen("MainMenuView", stage.getStage());
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
            throw new StageInitializerRuntimeException(ex);
        }
    }

    @Override
    public void getFirmeScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException {
        showScreen("FirmeView", event.getStage());
    }

    @Override
    public void getRobaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException {
        showScreen("RobaView", event.getStage());
    }

    @Override
    public void getIzdatnicaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException {
        showScreen("IzdatnicaView", event.getStage());
    }

    @Override
    public void getStavkaIzdatnicaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException {
        showScreen("StavkaIzdatnicaView", event.getStage());
    }

    @Override
    public void getStornoStavkaIzdatnicaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException {
        showScreen("StornoStavkaIzdatnicaView", event.getStage());
    }

    @Override
    public void getPrimkaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException {
        showScreen("PrimkaView", event.getStage());
    }

    @Override
    public void getStavkaPrimkaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException {
        showScreen("StavkaPrimkeView", event.getStage());
    }

    @Override
    public void getStornoStavkaPrimkaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException {
        showScreen("StornoStavkaPrimkeView", event.getStage());
    }

    @Override
    public void getRacunScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException {
        showScreen("RacunView", event.getStage());
    }

}
