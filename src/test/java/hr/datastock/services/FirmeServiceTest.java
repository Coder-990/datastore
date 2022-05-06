package hr.datastock.services;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.exceptions.FirmeEntityExistsRuntimeException;
import hr.datastock.exceptions.FirmeEntityNotFoundRuntimeException;
import hr.datastock.repositories.FirmeRepository;
import hr.datastock.services.impl.FirmeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirmeServiceTest {

    @Mock
    private FirmeRepository firmeRepository;

    @InjectMocks
    private FirmeServiceImpl firmeService;

    private static List<FirmeEntity> givenDataRecords() {
        return Arrays.asList(
                new FirmeEntity(1L, "45485474525", "Prime Software"),
                new FirmeEntity(11L, "586510002144", "TechFoot"),
                new FirmeEntity(13L, "02013025652", "KiloByte"),
                new FirmeEntity(14L, "47459652365", "CyberTech"),
                new FirmeEntity(24L, "54526589110", "Luminum"),
                new FirmeEntity(95L, "02013025652", "KiloBytetest")
        );
    }

    @Nested
    @DisplayName("FirmeService get all companies")
    class FirmeServiceTestAllCompanies {

        @Test
        @DisplayName("GIVEN firma records exists in database, WHEN all firma records are requested, THEN all firma records from database are returned.")
        void getAll() {
            when(firmeRepository.findAll())
                    .thenReturn(FirmeServiceTest.givenDataRecords());

            final List<FirmeEntity> expectedList = FirmeServiceTest.givenDataRecords();
            final List<FirmeEntity> actualList = firmeService.getAll();

            assertAll(
                    () -> assertNotNull(actualList),
                    () -> assertEquals(expectedList, actualList));
        }

        @Test
        @DisplayName("GIVEN there are no firma records in database, WHEN all firma records are requested, THEN empty list is returned.")
        void getAllEmpty() {
            when(firmeRepository.findAll())
                    .thenReturn(Collections.emptyList());

            final List<FirmeEntity> expectedList = Collections.emptyList();
            final List<FirmeEntity> actualList = firmeService.getAll();

            assertAll(
                    () -> assertNotNull(actualList),
                    () -> assertEquals(expectedList, actualList)
            );
        }
    }

    @Nested
    @DisplayName("FirmeService get company by id")
    class FirmeServiceTestGetOneById {

        @Test
        @DisplayName("GIVEN firma record exists in database, WHEN a single firma record is fetched, THEN the firma with requested ID is returned.")
        void getOneById() {
            when(firmeRepository.findById(1L))
                    .thenReturn(FirmeServiceTest.givenDataRecords().stream().filter(firmeEntity -> firmeEntity.getIdFirme() == 1L).findFirst());

            final Optional<FirmeEntity> expectedFE = FirmeServiceTest.givenDataRecords().stream().findFirst();
            final Optional<FirmeEntity> actualFE = firmeService.getOneById(1L);

            assertAll(
                    () -> assertNotNull(actualFE),
                    () -> assertEquals(expectedFE, actualFE)
            );
        }

        @Test
        @DisplayName("GIVEN firma record does not exists in database, WHEN a single firma record is fetched, THEN error is thrown.")
        void getOneByIdNonExistingId() {
            when(firmeRepository.findById(150L))
                    .thenThrow(new FirmeEntityNotFoundRuntimeException(150L));

            assertThrows(FirmeEntityNotFoundRuntimeException.class, () -> firmeService.getOneById(150L));
        }
    }

    @Nested
    @DisplayName("FirmeService get company by id")
    class FirmeServiceTestCreateFirma {

        @Test
        @DisplayName("GIVEN firma record does not exist in database, WHEN new firma record is created, THEN new record is created and returned.")
        void createNew() {
            final FirmeEntity expectedFirma = FirmeServiceTest.givenDataRecords().get(5);

            when(firmeRepository.save(any(FirmeEntity.class)))
                    .thenReturn(expectedFirma);

            when(firmeRepository.checkIfExistingOibIsInTableFirme(expectedFirma))
                    .thenReturn(Collections.emptyList());

            final FirmeEntity actualFirma = firmeService.createNew(expectedFirma);

            assertAll(
                    () -> assertNotNull(actualFirma),
                    () -> assertEquals(expectedFirma, actualFirma)
            );
        }

        @Test
        @DisplayName("GIVEN firma record exists in database, WHEN new firma record is created, THEN error is thrown.")
        void createNewExistingRecord() {
            final FirmeEntity expectedFirma =  FirmeServiceTest.givenDataRecords().get(3);

            when(firmeRepository.checkIfExistingOibIsInTableFirme(expectedFirma))
                    .thenReturn(FirmeServiceTest.givenDataRecords().subList(0,1));

            assertThrows(FirmeEntityExistsRuntimeException.class, () -> firmeService.createNew(expectedFirma));
        }
    }

    @Nested
    @DisplayName("CarBookingService update booking")
    class FirmeServiceTestUpdateExistingFirma {

        @Test
        @DisplayName("GIVEN firma record exists in database, WHEN a firma record is updated, THEN firma record is updated and returned.")
        void updateExisting() {
            final FirmeEntity existingFirmaWithUpdates = FirmeServiceTest.givenDataRecords().get(2);

            when(firmeRepository.findById(1L))
                    .thenReturn(FirmeServiceTest.givenDataRecords().stream().filter(firmeEntity -> firmeEntity.getIdFirme() == 1L).findFirst());

            when(firmeRepository.save(any(FirmeEntity.class))).thenReturn(existingFirmaWithUpdates);

            when(firmeRepository.checkIfExistingOibIsInTableFirme(new FirmeEntity(1L, "02013025652", "KiloByte")))
                    .thenReturn(Collections.emptyList());

            final FirmeEntity updatedFirmaEntity = firmeService.updateExisting(existingFirmaWithUpdates, 1L);

            assertAll(
                    ()-> assertNotNull(updatedFirmaEntity),
                    ()-> assertEquals(existingFirmaWithUpdates, updatedFirmaEntity)
            );
        }

        @Test
        @DisplayName("GIVEN firma record does not exist in database, WHEN a firma record is updated, THEN error is thrown.")
        void testUpdateNonExistingFirma() {
            final FirmeEntity existingFirmaWithUpdates = FirmeServiceTest.givenDataRecords().get(4);

            when(firmeRepository.findById(1L))
                    .thenThrow(FirmeEntityNotFoundRuntimeException.class);

            assertThrows(FirmeEntityNotFoundRuntimeException.class, () -> firmeService.updateExisting(existingFirmaWithUpdates, 1L));
        }
    }

    @Test
    @DisplayName("GIVEN firma record either exist or not, WHEN a single firma record is deleted, THEN repository delete method should be called once.")
    void deleteFirma() {
        final FirmeEntity firma = FirmeServiceTest.givenDataRecords().get(0);

        firmeRepository.delete(firma);

        verify(firmeRepository, times(1)).delete(firma);
    }
}