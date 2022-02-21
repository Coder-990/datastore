package hr.datastock.services.impl;

import hr.datastock.DatastockJavaFXAplication.StageReadyEvent;
import hr.datastock.exceptions.StageInitializerRuntimeException;
import hr.datastock.services.StageInitializerService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializerImpl implements StageInitializerService {

    @Value("classpath:/view/StornoStavkaPrimkeView.fxml")
    private Resource resourceStornoStavkaPrimke;

    @Value("classpath:/view/StavkaPrimkeView.fxml")
    private Resource resourceStavkaPrimke;

    @Value("classpath:/view/PrimkaView.fxml")
    private Resource resourcePrimka;

    @Value("classpath:/view/StornoStavkaIzdatnicaView.fxml")
    private Resource resourceStornoStavkaIzdatnica;

    @Value("classpath:/view/StavkaIzdatnicaView.fxml")
    private Resource resourceStavkaIzdatnica;

    @Value("classpath:/view/IzdatnicaView.fxml")
    private Resource resourceIzdatnica;

    @Value("classpath:/view/RobaView.fxml")
    private Resource resourceRoba;

    @Value("classpath:/view/FirmeView.fxml")
    private Resource resourceFirme;

    @Value("classpath:/view/ImprovedMainView.fxml")
    private Resource resourceMain;

    @Value("classpath:/view/LoginView.fxml")
    private Resource resourceLogin;

    @Value("classpath:/view/RacunView.fxml")
    private Resource resourceRacun;

    private final String applicationTitle;
    private final ApplicationContext applicationContext;

    public StageInitializerImpl(@Value("${spring.application.name}") final String applicationTitle, final ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onStartOfLogin(final StageReadyEvent event) {
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
    public void onStartOfMain(final StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.resourceMain.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            this.setStage(event, fxmlLoader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new StageInitializerRuntimeException(exception);
        }
    }

    private void setStage(final StageReadyEvent event, final Parent root) {
        Stage stage = event.getStage();
        stage.setTitle(this.applicationTitle);
        stage.setScene(new Scene(root, 980, 560));
        stage.show();
    }

    @Override
    public void onStartOfFirme() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceFirme.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfRoba() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceRoba.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfIzdatnica() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceIzdatnica.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfStavkaIzdatnica() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceStavkaIzdatnica.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfStornoStavkaIzdatnica() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceStornoStavkaIzdatnica.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfPrimka() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resourcePrimka.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfStavkaPrimka() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceStavkaPrimke.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfStornoStavkaPrimka() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceStornoStavkaPrimke.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }
    @Override
    public void onStartOfRacun() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.resourceRacun.getURL());
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        this.setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }
}
