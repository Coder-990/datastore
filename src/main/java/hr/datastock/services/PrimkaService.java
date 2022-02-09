package hr.datastock.services;

import hr.datastock.entities.PrimkaEntity;

import java.util.List;

public interface PrimkaService {

    List<PrimkaEntity> getAll();

    PrimkaEntity createPrimka(PrimkaEntity primka);

    void deletePrimka(Long id);
}
