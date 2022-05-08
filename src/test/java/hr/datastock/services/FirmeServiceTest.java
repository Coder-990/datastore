package hr.datastock.services;

import hr.datastock.HardcodedDataValues;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.exceptions.FirmeEntityExistsRuntimeException;
import hr.datastock.exceptions.FirmeEntityNotFoundRuntimeException;
import hr.datastock.repositories.FirmeRepository;
import hr.datastock.services.impl.FirmeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
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

    @Nested
    @DisplayName("FirmeService get all companies")
    class FirmeServiceTestAllCompanies {

        @Test
        @DisplayName("GIVEN firma records exists in database, WHEN all firma records are requested, THEN all firma records from database are returned.")
        void getAll() {
            final List<FirmeEntity> expectedList = HardcodedDataValues.givenFirmeDataRecords();
            final List<FirmeEntity> actualList = firmeService.getAll();

            when(firmeRepository.findAll()).thenReturn(HardcodedDataValues.givenFirmeDataRecords());

            assertAll(
                    () -> assertNotNull(actualList),
                    () -> assertEquals(expectedList, actualList));
        }

        @Test
        @DisplayName("GIVEN there are no firma records in database, WHEN all firma records are requested, THEN empty list is returned.")
        void getAllEmpty() {
            final List<FirmeEntity> expectedListOfFirma = Collections.emptyList();
            final List<FirmeEntity> actualListOfFirma = firmeService.getAll();

            when(firmeRepository.findAll()).thenReturn(Collections.emptyList());

            assertAll(
                    () -> assertNotNull(actualListOfFirma),
                    () -> assertEquals(expectedListOfFirma, actualListOfFirma)
            );
        }
    }

    @Nested
    @DisplayName("FirmeService get company by id")
    class FirmeServiceTestGetOneById {

        @Test
        @DisplayName("GIVEN firma record exists in database, WHEN a single firma record is fetched, THEN the firma with requested ID is returned.")
        void getOneById() {
            final Optional<FirmeEntity> expectedFirma = HardcodedDataValues.givenFirmeDataRecords().stream().findFirst();
            final Optional<FirmeEntity> actualFirma = firmeService.getOneById(1L);

            when(firmeRepository.findById(1L))
                    .thenReturn(HardcodedDataValues.givenFirmeDataRecords()
                            .stream().filter(firmeEntity -> firmeEntity.getIdFirme() == 1L).findFirst());

            assertAll(
                    () -> assertNotNull(actualFirma),
                    () -> assertEquals(expectedFirma, actualFirma)
            );
        }

        @Test
        @DisplayName("GIVEN firma record does not exists in database, WHEN a single firma record is fetched, THEN error is thrown.")
        void getOneByIdNonExistingId() {
            Class<FirmeEntityNotFoundRuntimeException> expectedExceptionClass = FirmeEntityNotFoundRuntimeException.class;
            Executable executable = () -> firmeService.getOneById(150L);

            when(firmeRepository.findById(150L)).thenThrow(new FirmeEntityNotFoundRuntimeException(150L));

            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Nested
    @DisplayName("FirmeService create firma")
    class FirmeServiceTestCreateFirma {

        @Test
        @DisplayName("GIVEN firma record does not exist in database, WHEN new firma record is created, THEN new record is created and returned.")
        void createNew() {
            final FirmeEntity expectedFirma = HardcodedDataValues.givenFirmeDataRecords().get(5);
            final FirmeEntity actualFirma = firmeService.createNew(expectedFirma);

            when(firmeRepository.save(any(FirmeEntity.class)))
                    .thenReturn(expectedFirma);
            when(firmeRepository.checkIfExistingOibIsInTableFirme(expectedFirma))
                    .thenReturn(Collections.emptyList());

            assertAll(
                    () -> assertNotNull(actualFirma),
                    () -> assertEquals(expectedFirma, actualFirma)
            );
        }

        @Test
        @DisplayName("GIVEN firma record exists in database, WHEN new firma record is created, THEN error is thrown.")
        void createNewExistingRecord() {
            final FirmeEntity expectedFirma =  HardcodedDataValues.givenFirmeDataRecords().get(3);
            Class<FirmeEntityExistsRuntimeException> expectedExceptionClass = FirmeEntityExistsRuntimeException.class;
            Executable executable = () -> firmeService.createNew(expectedFirma);

            when(firmeRepository.checkIfExistingOibIsInTableFirme(expectedFirma))
                    .thenReturn(HardcodedDataValues.givenFirmeDataRecords().subList(0,1));

            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Nested
    @DisplayName("FirmeService update firma")
    class FirmeServiceTestUpdateExistingFirma {

        @Test
        @DisplayName("GIVEN firma record exists in database, WHEN a firma record is updated, THEN firma record is updated and returned.")
        void updateExisting() {
            final FirmeEntity existingFirmaWithUpdates = HardcodedDataValues.givenFirmeDataRecords().get(2);
            final FirmeEntity updatedFirmaEntity = firmeService.updateExisting(existingFirmaWithUpdates, 1L);

            when(firmeRepository.findById(1L))
                    .thenReturn(HardcodedDataValues.givenFirmeDataRecords().stream().filter(firmeEntity -> firmeEntity.getIdFirme() == 1L).findFirst());
            when(firmeRepository.save(any(FirmeEntity.class))).thenReturn(existingFirmaWithUpdates);
            when(firmeRepository.checkIfExistingOibIsInTableFirme(new FirmeEntity(1L, "02013025652", "KiloByte")))
                    .thenReturn(Collections.emptyList());

            assertAll(
                    ()-> assertNotNull(updatedFirmaEntity),
                    ()-> assertEquals(existingFirmaWithUpdates, updatedFirmaEntity)
            );
        }

        @Test
        @DisplayName("GIVEN firma record does not exist in database, WHEN a firma record is updated, THEN error is thrown.")
        void testUpdateNonExistingFirma() {
            final FirmeEntity existingFirmaWithUpdates = HardcodedDataValues.givenFirmeDataRecords().get(4);
            Class<FirmeEntityNotFoundRuntimeException> expectedExceptionClass = FirmeEntityNotFoundRuntimeException.class;
            Executable executable = () -> firmeService.updateExisting(existingFirmaWithUpdates, 1L);

            when(firmeRepository.findById(1L))
                    .thenThrow(expectedExceptionClass);

            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Test
    @DisplayName("GIVEN firma record either exist or not, WHEN a single firma record is deleted, THEN repository delete method should be called once.")
    void deleteFirma() {
        final FirmeEntity firma = HardcodedDataValues.givenFirmeDataRecords().get(0);

        firmeRepository.delete(firma);

        verify(firmeRepository, times(1)).delete(firma);
    }
}