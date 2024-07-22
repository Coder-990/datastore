package hr.datastock;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class DatastoreApplication {

    public static void main(final String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("MYSQL_USER", Objects.requireNonNull(dotenv.get("MYSQL_USER")));
        System.setProperty("MYSQL_PASSWORD", Objects.requireNonNull(dotenv.get("MYSQL_PASSWORD")));
        System.setProperty("MYSQL_HOST", Objects.requireNonNull(dotenv.get("MYSQL_HOST")));
        System.setProperty("MYSQL_PORT", Objects.requireNonNull(dotenv.get("MYSQL_PORT")));
        System.setProperty("MYSQL_DB_NAME", Objects.requireNonNull(dotenv.get("MYSQL_DB_NAME")));

        Application.launch(DatastoreJavaFXApplication.class, args);
    }

}
