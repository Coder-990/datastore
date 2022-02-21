package hr.datastock;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class DatastockJavaFXAplication extends Application {

    private ConfigurableApplicationContext applcationContext;

    @Override
    public void init() {
        this.applcationContext = new SpringApplicationBuilder(DatastockApplication.class).run();
    }

    @Override
    public void stop() {
        this.applcationContext.close();
        Platform.exit();
    }

    @Override
    public void start(final Stage stage) {
        this.applcationContext.publishEvent(new StageReadyEvent(stage));
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
