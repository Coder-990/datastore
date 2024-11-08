package hr.datastore.services.impl;

import hr.datastore.entities.RacunEntity;
import hr.datastore.exceptions.RacunEntityUserIdCheckRuntimeExcpetion;
import hr.datastore.exceptions.RacunEntityUserPasswordCheckRuntimeExcpetion;
import hr.datastore.repositories.RacunRepository;
import hr.datastore.services.RacunService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RacunServiceImpl implements RacunService {

    private final RacunRepository racunRepository;

    @Override
    public Optional<RacunEntity> login(final String userid, final String password) {
        RacunEntity racun = this.racunRepository.findById(userid)
                .orElseThrow(() -> new RacunEntityUserIdCheckRuntimeExcpetion(userid));
        if (!password.equals(racun.getPassword())) {
            throw new RacunEntityUserPasswordCheckRuntimeExcpetion(password);
        }
        return Optional.of(racun);
    }

    @Override
    public RacunEntity createAccount(final RacunEntity racun) {
        return this.racunRepository.save(racun);
    }

}
