package hr.datastock.services.impl;

import hr.datastock.entities.PrimkaEntity;
import hr.datastock.repositories.PrimkaRepository;
import hr.datastock.services.PrimkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrimkaServiceImpl implements PrimkaService {

    @Autowired
    private PrimkaRepository primkaRepository;

    @Override
    public List<PrimkaEntity> getAll(){
        return  this.primkaRepository.findAll();
    }

    @Override
    public PrimkaEntity createPrimka(final PrimkaEntity primka){
        return this.primkaRepository.save(primka);
    }

    @Override
    public void deletePrimka(final Long id){
        this.primkaRepository.deleteById(id);
    }
}
