package hr.datastore;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class DatastoreJavaFXApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        this.applicationContext = new SpringApplicationBuilder(DatastoreApplication.class).run();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(final Stage stage) {
        this.applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    public static class StageReadyEvent extends ApplicationEvent {

        public StageReadyEvent(final Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return ((Stage) this.getSource());
        }
    }
}
