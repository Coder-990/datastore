package hr.datastore.services.impl;

import hr.datastore.entities.PrimkaEntity;
import hr.datastore.repositories.PrimkaRepository;
import hr.datastore.services.PrimkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PrimkaServiceImpl implements PrimkaService {

    private final PrimkaRepository primkaRepository;

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
