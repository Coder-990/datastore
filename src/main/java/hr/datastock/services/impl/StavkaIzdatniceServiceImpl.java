package hr.datastock.services.impl;

import hr.datastock.entities.StavkaIzdatniceEntity;
import hr.datastock.repositories.StavkaIzdatniceRepository;
import hr.datastock.services.StavkaIzdatniceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StavkaIzdatniceServiceImpl implements StavkaIzdatniceService {

    @Autowired
    StavkaIzdatniceRepository stavkaIzdatniceRepository;

    @Override
    public List<StavkaIzdatniceEntity> getAll(){
        return  stavkaIzdatniceRepository.findAll();
    }

    @Override
    public StavkaIzdatniceEntity createStavkaIzdatnice(StavkaIzdatniceEntity izdatnica){
        return stavkaIzdatniceRepository.save(izdatnica);
    }

}
