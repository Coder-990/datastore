package hr.datastock.services.impl;

import hr.datastock.entities.RacunEntity;
import hr.datastock.exceptions.RacunEntityUserIdCheckRuntimeExcpetion;
import hr.datastock.exceptions.RacunEntityUserPasswordCheckRuntimeExcpetion;
import hr.datastock.repositories.RacunRepository;
import hr.datastock.services.RacunService;
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
