package hr.datastock;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatastoreApplication {

    public static void main(final String[] args) {
        Application.launch(DatastoreJavaFXApplication.class, args);
    }

}
