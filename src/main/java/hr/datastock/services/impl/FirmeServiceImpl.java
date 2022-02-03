package hr.datastock.services.impl;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.repositories.FirmeRepository;
import hr.datastock.services.FirmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;
import java.util.List;

@Component
public class FirmeServiceImpl implements FirmeService {

    @Autowired
    private FirmeRepository firmeRepository;

    @Override
    public List<FirmeEntity> getAll() {
        return firmeRepository.findAll();
    }

    @Override
    public Long getById(Long id){
        return firmeRepository.findById(id).get().getIdFirme();
    }

    @Override
    public FirmeEntity createCompany(FirmeEntity company) {
        return firmeRepository.save(company);
    }

    @Override
    public FirmeEntity updateCompany(FirmeEntity updateCompany, Long id) {
        return firmeRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setOibFirme(updateCompany.getOibFirme());
                    existingCompany.setNazivFirme(updateCompany.getNazivFirme());
                    return firmeRepository.saveAndFlush(existingCompany);
                }).orElseThrow(EntityExistsException::new);
    }

    @Override
    public void deleteCompany(Long id) {
        firmeRepository.deleteById(id);
    }
}
