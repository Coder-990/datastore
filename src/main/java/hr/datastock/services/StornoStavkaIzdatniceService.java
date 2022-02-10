package hr.datastock.services;

import hr.datastock.entities.StornoStavkaIzdatniceEntity;

import java.util.List;

public interface StornoStavkaIzdatniceService {
    List<StornoStavkaIzdatniceEntity> getAll();

    StornoStavkaIzdatniceEntity createStornoStavkeIzdatnica(StornoStavkaIzdatniceEntity stornoStavkaIzdatnice);

    void deleteStornoStavkeIzdatnica(Long id);
}
