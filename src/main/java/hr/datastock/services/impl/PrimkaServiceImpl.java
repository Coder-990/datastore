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
        return  primkaRepository.findAll();
    }

    @Override
    public PrimkaEntity createPrimka(PrimkaEntity primka){
        return primkaRepository.save(primka);
    }

    @Override
    public void deletePrimka(Long id){
        primkaRepository.deleteById(id);
    }
}
