package hr.datastock.services.impl;

import hr.datastock.entities.StornoStavkaIzdatniceEntity;
import hr.datastock.repositories.StornoStavkaIzdatniceRepository;
import hr.datastock.services.StornoStavkaIzdatniceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StornoStavkaIzdatniceServiceImpl implements StornoStavkaIzdatniceService {

    @Autowired
    StornoStavkaIzdatniceRepository stornoStavkaIzdatniceRepository;

    @Override
    public List<StornoStavkaIzdatniceEntity> getAll(){
        return  stornoStavkaIzdatniceRepository.findAll();
    }

    @Override
    public StornoStavkaIzdatniceEntity createStornoStavkeIzdatnica(StornoStavkaIzdatniceEntity stornoStavkaIzdatnice){
        return stornoStavkaIzdatniceRepository.save(stornoStavkaIzdatnice);
    }

    @Override
    public void deleteStornoStavkeIzdatnica(Long id){
        stornoStavkaIzdatniceRepository.deleteById(id);
    }
}
