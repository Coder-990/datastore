package hr.datastock.services;

import hr.datastock.DatastockJavaFXAplication.StageReadyEvent;
import java.io.IOException;

public interface StageInitializerService {

    void onStartOfLogin(final StageReadyEvent event);

    void onStartOfMain(final StageReadyEvent event);

    void onStartOfFirme() throws IOException;

    void onStartOfRoba() throws IOException;

    void onStartOfIzdatnica() throws IOException;

    void onStartOfStavkaIzdatnica() throws IOException;

    void onStartOfStornoStavkaIzdatnica() throws IOException;

    void onStartOfPrimka() throws IOException;

    void onStartOfStavkaPrimka() throws IOException;

    void onStartOfStornoStavkaPrimka() throws IOException;
}
