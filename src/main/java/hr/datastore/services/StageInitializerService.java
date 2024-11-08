package hr.datastore.services;

import hr.datastore.DatastoreJavaFXApplication;

import java.io.IOException;

public interface StageInitializerService {

    void getLoginScreen(final DatastoreJavaFXApplication.StageReadyEvent event);

    void getMainMenuScreen(final DatastoreJavaFXApplication.StageReadyEvent event);

    void getFirmeScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException;

    void getRobaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException;

    void getIzdatnicaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException;

    void getStavkaIzdatnicaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException;

    void getStornoStavkaIzdatnicaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException;

    void getPrimkaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException;

    void getStavkaPrimkaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException;

    void getStornoStavkaPrimkaScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException;

    void getRacunScreen(final DatastoreJavaFXApplication.StageReadyEvent event) throws IOException;
}
