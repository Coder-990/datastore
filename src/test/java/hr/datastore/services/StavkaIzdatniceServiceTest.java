package hr.datastore.services;

import hr.datastore.MockEntityDataValues;
import hr.datastore.entities.StavkaIzdatniceEntity;
import hr.datastore.repositories.StavkaIzdatniceRepository;
import hr.datastore.services.impl.StavkaIzdatniceServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StavkaIzdatniceServiceTest {

    @Mock
    private StavkaIzdatniceRepository stavkaIzdatniceRepository;

    @InjectMocks
    private StavkaIzdatniceServiceImpl stavkaIzdatniceService;

    @Nested
    @DisplayName("StavkaIzdatnicaService get all shipments")
    class StavkaIzdatnicaServiceTestGetAllItemShipments {

        @Test
        @DisplayName("GIVEN stavkaizdatnice records exists in database, WHEN all stavkaizdatnice records are requested, THEN all stavkaizdatnice records from database are returned.")
        void testGetAll() {
            final List<StavkaIzdatniceEntity> expectedListOfStavkaIzdatnice = MockEntityDataValues.givenStavkaIzdatniceDataRecords();

            when(stavkaIzdatniceRepository.findAll()).thenReturn(expectedListOfStavkaIzdatnice);
            final List<StavkaIzdatniceEntity> actualListOfStavkaIzdatnice = stavkaIzdatniceService.getAll();

            assertAll(
                    () -> assertNotNull(actualListOfStavkaIzdatnice),
                    () -> assertEquals(expectedListOfStavkaIzdatnice, actualListOfStavkaIzdatnice)
            );
        }

        @Test
        @DisplayName("GIVEN there are no stavkaIzdatnice records in database, WHEN all stavkaIzdatnice records are requested, THEN empty list is returned.")
        void testGetAllEmpty() {
            final List<StavkaIzdatniceEntity> expectedListOfStavkaIzdatnice = Collections.emptyList();

            when(stavkaIzdatniceRepository.findAll()).thenReturn(expectedListOfStavkaIzdatnice);
            final List<StavkaIzdatniceEntity> actualListOfStavkaIzdatnice = stavkaIzdatniceService.getAll();

            assertAll(
                    () -> assertNotNull(actualListOfStavkaIzdatnice),
                    () -> assertEquals(expectedListOfStavkaIzdatnice, actualListOfStavkaIzdatnice)
            );
        }
    }

    @Nested
    @DisplayName("StavkaIzdatnicaService create stavkaIzdatnice")
    class StavkaIzdatnicaServiceTestCreateItemShipments {

        @Test
        @DisplayName("GIVEN stornoStavkaIzdatnice record exists in database, WHEN a stornoStavkaIzdatnice record is created, THEN stornoStavkaIzdatnice record is returned.")
        void createStavkaIzdatnice() {
            final StavkaIzdatniceEntity expectedStavkaIzdatnice = MockEntityDataValues.givenStavkaIzdatniceDataRecords().get(0);

            when(stavkaIzdatniceRepository.save(any(StavkaIzdatniceEntity.class)))
                    .thenReturn(expectedStavkaIzdatnice);

            final StavkaIzdatniceEntity actualStavkaIzdatnica = stavkaIzdatniceService.createStavkaIzdatnice(expectedStavkaIzdatnice);

            assertAll(
                    () -> assertNotNull(actualStavkaIzdatnica),
                    () -> assertEquals(expectedStavkaIzdatnice, actualStavkaIzdatnica)
            );
        }
    }

    @Nested
    @DisplayName("StornoStavkaIzdatnicaService cancel stornoStavkaIzdatnice")
    class StavkaIzdatnicaServiceTestCancelItemShipments {
        @Test
        @DisplayName("GIVEN stornoStavkaIzdatnice record exists in database, WHEN a stornoStavkaIzdatnice record is canceled/updated, THEN stornoStavkaIzdatnice record is canceled and returned.")
       void createStornoStavkeIzdatnice() {
            final StavkaIzdatniceEntity expectedStavkaIzdatnice = MockEntityDataValues.givenStavkaIzdatniceDataRecords().get(2);

            when(stavkaIzdatniceRepository.findById(104L))
                    .thenReturn(MockEntityDataValues.givenStavkaIzdatniceDataRecords()
                            .stream()
                            .filter(stavkaIzdatniceEntity -> stavkaIzdatniceEntity.getIdStavkaIzdatnice() == 104L).findFirst());
            when(stavkaIzdatniceRepository.save(any(StavkaIzdatniceEntity.class)))
                    .thenReturn(expectedStavkaIzdatnice);

            final StavkaIzdatniceEntity actualStavkaIzdatnice = stavkaIzdatniceService.createStornoStavkeIzdatnice(expectedStavkaIzdatnice).get();

            assertAll(
                    () -> assertNotNull(actualStavkaIzdatnice),
                    () -> assertEquals(expectedStavkaIzdatnice, actualStavkaIzdatnice)
            );
        }
    }
}