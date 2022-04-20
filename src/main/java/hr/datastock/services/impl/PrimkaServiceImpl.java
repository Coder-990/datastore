package hr.datastock.services.impl;

import hr.datastock.entities.PrimkaEntity;
import hr.datastock.repositories.PrimkaRepository;
import hr.datastock.services.PrimkaService;
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
