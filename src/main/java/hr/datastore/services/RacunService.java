package hr.datastore.services;

import hr.datastore.entities.RacunEntity;

import java.util.Optional;

public interface RacunService {

    Optional<RacunEntity> login(final String userId, final String password);

    RacunEntity createAccount(RacunEntity racun);
}
