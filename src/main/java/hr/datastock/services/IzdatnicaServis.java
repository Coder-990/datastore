package hr.datastock.services;

import hr.datastock.entities.IzdatnicaEntity;

import java.util.List;

public interface IzdatnicaServis {

    List<IzdatnicaEntity> getAll();

    IzdatnicaEntity createIzdatnica(IzdatnicaEntity izdatnica);

    IzdatnicaEntity updateIzdatnica(IzdatnicaEntity izdatnica, Long id);

    void deleteIzdatnica(Long id);
}
