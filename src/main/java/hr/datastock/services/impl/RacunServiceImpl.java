package hr.datastock.services.impl;

import hr.datastock.entities.RacunEntity;
import hr.datastock.exceptions.RacunEntityRuntimeExcpetion;
import hr.datastock.repositories.RacunRepository;
import hr.datastock.services.RacunService;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RacunServiceImpl implements RacunService {

    @Autowired
    private RacunRepository racunRepository;

    @Override
    public void login(String userid, String password) {

        if (StringUtils.isEmpty(userid)) {
            throw new RacunEntityRuntimeExcpetion("Please enter loginId!");
        }
        if (StringUtils.isEmpty(password)) {
            throw new RacunEntityRuntimeExcpetion("Please enter user password!");
        }

        RacunEntity racun = racunRepository.findById(userid)
                .orElseThrow(() -> new RacunEntityRuntimeExcpetion("Please check your login id!"));

        if (!password.equals(racun.getPassword())) {
            throw new RacunEntityRuntimeExcpetion("Please enter correct password!");
        }
    }
}
