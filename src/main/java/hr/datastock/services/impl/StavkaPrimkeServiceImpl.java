package hr.datastock.services.impl;

import hr.datastock.entities.StavkaPrimkeEntity;
import hr.datastock.repositories.StavkaPrimkeRepository;
import hr.datastock.services.StavkaPrimkeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StavkaPrimkeServiceImpl implements StavkaPrimkeService {

    @Autowired
    StavkaPrimkeRepository stavkaPrimkeRepository;

    @Override
    public List<StavkaPrimkeEntity> getAll(){
        return  stavkaPrimkeRepository.findAll();
    }

    @Override
    public StavkaPrimkeEntity createStavkaIzdatnice(StavkaPrimkeEntity primka){
        return stavkaPrimkeRepository.save(primka);
    }

}
