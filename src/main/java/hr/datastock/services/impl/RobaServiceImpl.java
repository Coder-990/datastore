package hr.datastock.services.impl;

import hr.datastock.entities.RobaEntity;
import hr.datastock.exceptions.RobaEntityExistsRuntimeException;
import hr.datastock.repositories.RobaRepository;
import hr.datastock.services.RobaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RobaServiceImpl implements RobaService {

    private final RobaRepository robaRepository;
    @Override
    public List<RobaEntity> getAll() {
        return this.robaRepository.findAll();
    }
    @Override
    public Optional<RobaEntity> getOneById(final Long id) {
        return this.robaRepository.findById(id);
    }
    @Override
    public RobaEntity createArticle(final RobaEntity roba) {
        return this.saveArticle(roba);
    }
    @Override
    public RobaEntity updateExistingArticle(final RobaEntity newArticleValue, final Long id) {
        return this.getOneById(id)
                .map(existingRoba -> {
                    existingRoba.setNazivArtikla(newArticleValue.getNazivArtikla());
                    existingRoba.setKolicina(newArticleValue.getKolicina());
                    existingRoba.setCijena(newArticleValue.getCijena());
                    existingRoba.setOpis(newArticleValue.getOpis());
                    existingRoba.setJmj(newArticleValue.getJmj());
                    return this.saveArticle(existingRoba);
                }).orElseThrow(() -> new RobaEntityExistsRuntimeException(newArticleValue));
    }
    @Override
    public void deleteRoba(final Long id) {
        this.robaRepository.deleteById(id);
    }

    private RobaEntity saveArticle(RobaEntity robaEntity){
        if (robaEntity.getIdRobe() != null){
            List<RobaEntity> nazivArtiklaOverlap = robaRepository.checkIfExistingArticleNameIsInTableRoba(robaEntity);
            if (!nazivArtiklaOverlap.isEmpty()){
                throw new RobaEntityExistsRuntimeException(nazivArtiklaOverlap.get(0));
            }
        }
        return robaRepository.save(robaEntity);
    }
}
