package hr.datastore;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static hr.datastore.services.helper.env.EnvFile.createEnvFileIfNotExists;
import static hr.datastore.services.helper.env.EnvFile.loadEnvFiles;

@SpringBootApplication
public class DatastoreApplication {

    public static void main(final String[] args) {
        createEnvFileIfNotExists();
        loadEnvFiles();
        Application.launch(DatastoreJavaFXApplication.class, args);
    }



}
