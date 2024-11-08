package hr.datastore.services;

import hr.datastore.entities.StavkaPrimkeEntity;

import java.util.List;
import java.util.Optional;

public interface StavkaPrimkeService {

    List<StavkaPrimkeEntity> getAll();

    StavkaPrimkeEntity createStavkaPrimke(final StavkaPrimkeEntity primka);

    Optional<StavkaPrimkeEntity> createStornoStavkePrimke(final StavkaPrimkeEntity stavkaPrimke);
}
