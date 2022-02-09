package hr.datastock.services.impl;

import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.exceptions.IzdatnicaEntityExistsRuntimeException;
import hr.datastock.repositories.IzdatnicaRepository;
import hr.datastock.services.IzdatnicaServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IzdatnicaServisImpl implements IzdatnicaServis {

    @Autowired
    private IzdatnicaRepository izdatnicaRepository;

    @Override
    public List<IzdatnicaEntity> getAll(){
       return  izdatnicaRepository.findAll();
    }

    @Override
    public IzdatnicaEntity createIzdatnica(IzdatnicaEntity izdatnica){
        return izdatnicaRepository.save(izdatnica);
    }

    @Override
    public IzdatnicaEntity updateIzdatnica(IzdatnicaEntity izdatnica, Long id){
        return izdatnicaRepository.findById(id)
                .map(existingIzdatnica ->{
                    existingIzdatnica.setIzdatnicaFirme(izdatnica.getIzdatnicaFirme());
                    existingIzdatnica.setDatum(izdatnica.getDatum());
                    return izdatnicaRepository.saveAndFlush(existingIzdatnica);
                }).orElseThrow(() -> new IzdatnicaEntityExistsRuntimeException(id));
    }

    @Override
    public void deleteIzdatnica(Long id){
        izdatnicaRepository.deleteById(id);
    }
}
