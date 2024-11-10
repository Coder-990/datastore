package hr.datastore.services.helper.env;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class EnvFile {

    private EnvFile() {
    }

    public static void createEnvFileIfNotExists() {
        File envFile = new File(".env");

        if (!envFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(envFile))) {
                writer.write("MYSQL_ROOT_PASSWORD=root\n");
                writer.write("MYSQL_USER=root\n");
                writer.write("MYSQL_PASSWORD=root\n");
                writer.write("MYSQL_DB_NAME=datastore\n");
                writer.write("MYSQL_DB_SCHEMA=datastore\n");
                writer.write("MYSQL_PORT=3306\n");
                writer.write("MYSQL_HOST=localhost\n");
                log.info(".env file created successfully.");
            } catch (IOException e) {
                log.info("Failed to create .env file: {}", e.getMessage());
            }
        } else {
            log.info(".env file already exists. Skipping creation.");
        }
    }

    public static void loadEnvFiles() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("MYSQL_USER", Objects.requireNonNull(dotenv.get("MYSQL_USER")));
        System.setProperty("MYSQL_PASSWORD", Objects.requireNonNull(dotenv.get("MYSQL_PASSWORD")));
        System.setProperty("MYSQL_HOST", Objects.requireNonNull(dotenv.get("MYSQL_HOST")));
        System.setProperty("MYSQL_PORT", Objects.requireNonNull(dotenv.get("MYSQL_PORT")));
        System.setProperty("MYSQL_DB_NAME", Objects.requireNonNull(dotenv.get("MYSQL_DB_NAME")));
        System.setProperty("MYSQL_DB_SCHEMA", Objects.requireNonNull(dotenv.get("MYSQL_DB_SCHEMA")));
    }
}
