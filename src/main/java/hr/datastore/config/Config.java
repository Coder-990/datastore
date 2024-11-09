package hr.datastore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${datastore.view-path}")
    private String viewPath;

    @Bean
    public String viewPath() {
        return viewPath;
    }

    @Value("${spring.application.name}")
    private String applicationTitle;

    @Bean
    public String applicationTitle() {
        return applicationTitle;
    }
}
