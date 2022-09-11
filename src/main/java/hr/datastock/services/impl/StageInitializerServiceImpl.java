package hr.datastock.services.impl;

import hr.datastock.DatastoreJavaFXApplication.StageReadyEvent;
import hr.datastock.exceptions.StageInitializerRuntimeException;
import hr.datastock.services.StageInitializerService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static hr.datastock.controllers.service.impl.Const.CLASSPATH_VIEW;
import static hr.datastock.controllers.service.impl.Const.FXML;

@Slf4j
@Service
public class StageInitializerServiceImpl implements StageInitializerService {

    private final String applicationTitle;
    private final ApplicationContext applicationContext;

    public StageInitializerServiceImpl(@Value("${spring.application.name}") final String applicationTitle,
                                       final ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Value(CLASSPATH_VIEW + "StornoStavkaPrimkeView" + FXML)
    private Resource resourceStornoStavkaPrimke;

    @Value(CLASSPATH_VIEW + "StavkaPrimkeView" + FXML)
    private Resource resourceStavkaPrimke;

    @Value(CLASSPATH_VIEW + "PrimkaView" + FXML)
    private Resource resourcePrimka;

    @Value(CLASSPATH_VIEW + "StornoStavkaIzdatnicaView" + FXML)
    private Resource resourceStornoStavkaIzdatnica;

    @Value(CLASSPATH_VIEW + "StavkaIzdatnicaView" + FXML)
    private Resource resourceStavkaIzdatnica;

    @Value(CLASSPATH_VIEW + "IzdatnicaView" + FXML)
    private Resource resourceIzdatnica;

    @Value(CLASSPATH_VIEW + "RobaView" + FXML)
    private Resource resourceRoba;

    @Value(CLASSPATH_VIEW + "FirmeView" + FXML)
    private Resource resourceFirme;

    @Value(CLASSPATH_VIEW + "MainMenuView" + FXML)
    private Resource resourceMain;

    @Value(CLASSPATH_VIEW + "RacunView" + FXML)
    private Resource resourceRacun;

    @Value(CLASSPATH_VIEW + "LoginView" + FXML)
    private Resource resourceLogin;

    @Override
    public void getLoginScreen(final StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.resourceLogin.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            this.setStage(event, fxmlLoader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new StageInitializerRuntimeException(exception);
        }
    }

    @Override
    public void getMainMenuScreen(final StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.resourceMain.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            this.setStage(event, fxmlLoader.load());
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    @Override
    public void getFirmeScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceFirme.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void getRobaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceRoba.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void getIzdatnicaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceIzdatnica.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void getStavkaIzdatnicaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceStavkaIzdatnica.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void getStornoStavkaIzdatnicaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceStornoStavkaIzdatnica.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void getPrimkaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resourcePrimka.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void getStavkaPrimkaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceStavkaPrimke.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void getStornoStavkaPrimkaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceStornoStavkaPrimke.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void getRacunScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceRacun.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    private void setStage(final StageReadyEvent event, final Parent root) {
        Stage stage = event.getStage();
        stage.setTitle(this.applicationTitle);
        stage.setScene(new Scene(root, 980, 560));
        stage.show();
    }
}
