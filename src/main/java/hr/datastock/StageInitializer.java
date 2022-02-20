package hr.datastock;

import hr.datastock.exceptions.StageInitializerRuntimeException;
import hr.datastock.services.StageInitializerService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static hr.datastock.DatastockJavaFXAplication.StageReadyEvent;


@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent>, StageInitializerService {

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

    @Value("classpath:/view/MainView.fxml")
    private Resource resourceMain;

    @Value("classpath:/view/LoginView.fxml")
    private Resource resourceLogin;

    private final String applicationTitle;
    private final ApplicationContext applicationContext;

    public StageInitializer(@Value("${spring.application.name}") String applicationTitle, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        onStartOfLogin(event);
    }

    @Override
    public void onStartOfLogin(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resourceLogin.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            setStage(event, fxmlLoader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new StageInitializerRuntimeException(exception);
        }
    }

    @Override
    public void onStartOfMain(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resourceMain.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            setStage(event, fxmlLoader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new StageInitializerRuntimeException(exception);
        }
    }

    private void setStage(StageReadyEvent event, Parent root) {
        Stage stage = event.getStage();
        stage.setTitle(applicationTitle);
        stage.setScene(new Scene(root, 860, 460));
        stage.show();
    }

    @Override
    public void onStartOfFirme() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resourceFirme.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfRoba() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resourceRoba.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfIzdatnica() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resourceIzdatnica.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfStavkaIzdatnica() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resourceStavkaIzdatnica.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfStornoStavkaIzdatnica() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resourceStornoStavkaIzdatnica.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfPrimka() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resourcePrimka.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfStavkaPrimka() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resourceStavkaPrimke.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }

    @Override
    public void onStartOfStornoStavkaPrimka() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resourceStornoStavkaPrimke.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        setStage(new StageReadyEvent(new Stage()), fxmlLoader.load());
    }
}


