package hr.datastock.services;

import hr.datastock.MockEntityDataValues;
import hr.datastock.entities.RacunEntity;
import hr.datastock.repositories.RacunRepository;
import hr.datastock.repositories.StavkaIzdatniceRepository;
import hr.datastock.services.impl.RacunServiceImpl;
import javafx.beans.binding.Bindings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RacunServiceTest {

    @Mock
    private RacunRepository racunRepository;

    @InjectMocks
    private RacunServiceImpl racunService;

    @Nested
    @DisplayName("RacunService get all shipments")
    class StavkaIzdatnicaServiceTestGetAllItemShipments {

        @Test
        void login() {
            final List<RacunEntity> expectedRacun = MockEntityDataValues.givenRacunDataRecords();
            when(racunRepository.findAll()).thenReturn(expectedRacun);

//           final List<RacunEntity> actuaList = racunService.login(expectedRacun.stream().map(RacunEntity::getUserId).findFirst().get(), expectedRacun.stream().map(RacunEntity::getPassword).findFirst().get());
        }
    }

    @Test
    void createAccount() {

    }
}