package hr.datastock.services;

import hr.datastock.entities.RacunEntity;

public interface RacunService {

    void login(final String userId, final String password);

    RacunEntity createAccount(RacunEntity racun);
}
