package hr.datastock;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatastockApplication {

    public static void main(final String[] args) {
        Application.launch(DatastockJavaFXAplication.class, args);
    }

}
