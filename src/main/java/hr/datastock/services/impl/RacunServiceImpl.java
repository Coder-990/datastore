package hr.datastock.services.impl;

import hr.datastock.entities.RacunEntity;
import hr.datastock.exceptions.RacunEntityRuntimeExcpetion;
import hr.datastock.repositories.RacunRepository;
import hr.datastock.services.RacunService;
import lombok.RequiredArgsConstructor;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class RacunServiceImpl implements RacunService {

    private final RacunRepository racunRepository;

    @Override
    public void login(final String userid, final String password) {

        if (StringUtils.isEmpty(userid)) {
            throw new RacunEntityRuntimeExcpetion("Please enter loginId!");
        }
        if (StringUtils.isEmpty(password)) {
            throw new RacunEntityRuntimeExcpetion("Please enter user password!");
        }

        RacunEntity racun = this.racunRepository.findById(userid)
                .orElseThrow(() -> new RacunEntityRuntimeExcpetion("Please check your login id!"));

        if (!password.equals(racun.getPassword())) {
            throw new RacunEntityRuntimeExcpetion("Please enter correct password!");
        }
    }

    @Override
    public RacunEntity createAccount(final RacunEntity racun){
        return this.racunRepository.save(racun);
    }
}
