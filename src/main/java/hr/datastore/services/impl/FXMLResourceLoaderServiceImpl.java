package hr.datastore.services.impl;

import hr.datastore.services.FXMLResourceLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FXMLResourceLoaderServiceImpl implements FXMLResourceLoaderService {

    private static final String FXML_EXTENSION = ".fxml";

    private final String viewPath;
    private final ResourceLoader resourceLoader;

    @Override
    public Resource getResource(String filename) {
        return resourceLoader.getResource(viewPath + filename + FXML_EXTENSION);
    }
}
