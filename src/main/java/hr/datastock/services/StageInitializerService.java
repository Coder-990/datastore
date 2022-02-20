package hr.datastock.services;

import hr.datastock.DatastockJavaFXAplication;

import java.io.IOException;

public interface StageInitializerService {

    void onStartOfLogin(DatastockJavaFXAplication.StageReadyEvent event);

    void onStartOfMain(DatastockJavaFXAplication.StageReadyEvent event);

    void onStartOfFirme() throws IOException;

    void onStartOfRoba() throws IOException;

    void onStartOfIzdatnica() throws IOException;

    void onStartOfStavkaIzdatnica() throws IOException;

    void onStartOfStornoStavkaIzdatnica() throws IOException;

    void onStartOfPrimka() throws IOException;

    void onStartOfStavkaPrimka() throws IOException;

    void onStartOfStornoStavkaPrimka() throws IOException;
}
