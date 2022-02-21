package hr.datastock.services.impl;

import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.repositories.IzdatnicaRepository;
import hr.datastock.services.IzdatnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IzdatnicaServiceImpl implements IzdatnicaService {

    @Autowired
    private IzdatnicaRepository izdatnicaRepository;

    @Override
    public List<IzdatnicaEntity> getAll(){
       return  this.izdatnicaRepository.findAll();
    }

    @Override
    public IzdatnicaEntity createIzdatnica(final IzdatnicaEntity izdatnica){
        return this.izdatnicaRepository.save(izdatnica);
    }

    @Override
    public void deleteIzdatnica(final Long id){
        this.izdatnicaRepository.deleteById(id);
    }
}
