package hr.datastock;

import hr.datastock.exceptions.StageInitializerRuntimeException;
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
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/view/PrimkaView.fxml")
//    @Value("classpath:/view/IzdatnicaView.fxml")
//    @Value("classpath:/view/FirmeView.fxml")
    private Resource resource;
    private final String applicationTitle;
    private final ApplicationContext applicationContext;

    public StageInitializer(@Value("${spring.application.name}") String applicationTitle, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setTitle(applicationTitle);
            stage.setScene(new Scene(root, 760, 460));
            stage.show();
        } catch (IOException e) {
            throw new StageInitializerRuntimeException(new Stage());
        }

    }
}


