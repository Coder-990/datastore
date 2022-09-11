package hr.datastock.services;

import hr.datastock.DatastoreJavaFXApplication.StageReadyEvent;
import java.io.IOException;

public interface StageInitializerService {

    void getLoginScreen(final StageReadyEvent event);

    void getMainMenuScreen(final StageReadyEvent event);

    void getFirmeScreen() throws IOException;

    void getRobaScreen() throws IOException;

    void getIzdatnicaScreen() throws IOException;

    void getStavkaIzdatnicaScreen() throws IOException;

    void getStornoStavkaIzdatnicaScreen() throws IOException;

    void getPrimkaScreen() throws IOException;

    void getStavkaPrimkaScreen() throws IOException;

    void getStornoStavkaPrimkaScreen() throws IOException;

    void getRacunScreen() throws IOException;
}
