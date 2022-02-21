package hr.datastock.services;

import hr.datastock.entities.IzdatnicaEntity;

import java.util.List;

public interface IzdatnicaService {

    List<IzdatnicaEntity> getAll();

    IzdatnicaEntity createIzdatnica(final IzdatnicaEntity izdatnica);

    void deleteIzdatnica(final Long id);
}
