package hr.datastore.services;

import org.springframework.core.io.Resource;

public interface FXMLResourceLoaderService {
    Resource getResource(String filename);
}
