package hr.datastore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaFXConfig {

    @Value("${datastore.view-path}")
    private String viewPath;

    @Bean
    public String viewPath() {
        return viewPath;
    }
}
