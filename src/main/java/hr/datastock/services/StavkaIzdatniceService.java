package hr.datastock.services;

import hr.datastock.entities.StavkaIzdatniceEntity;

import java.util.List;

public interface StavkaIzdatniceService {

    List<StavkaIzdatniceEntity> getAll();

    StavkaIzdatniceEntity createStavkaIzdatnice(StavkaIzdatniceEntity izdatnica);
}
