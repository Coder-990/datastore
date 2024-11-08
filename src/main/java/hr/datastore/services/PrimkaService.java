package hr.datastore.services;

import hr.datastore.entities.PrimkaEntity;

import java.util.List;

public interface PrimkaService {

    List<PrimkaEntity> getAll();

    PrimkaEntity createPrimka(final PrimkaEntity primka);

    void deletePrimka(final Long id);
}
