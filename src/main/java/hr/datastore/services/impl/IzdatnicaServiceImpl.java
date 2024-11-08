package hr.datastore.services.impl;

import hr.datastore.entities.IzdatnicaEntity;
import hr.datastore.repositories.IzdatnicaRepository;
import hr.datastore.services.IzdatnicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IzdatnicaServiceImpl implements IzdatnicaService {

    private final IzdatnicaRepository izdatnicaRepository;

    @Override
    public List<IzdatnicaEntity> getAll() {
        return this.izdatnicaRepository.findAll();
    }

    @Override
    public IzdatnicaEntity createIzdatnica(final IzdatnicaEntity izdatnica) {
        return this.izdatnicaRepository.save(izdatnica);
    }

    @Override
    public void deleteIzdatnica(final Long id) {
        this.izdatnicaRepository.deleteById(id);
    }
}
