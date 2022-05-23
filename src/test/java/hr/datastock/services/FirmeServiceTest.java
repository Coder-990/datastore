package hr.datastock.services;

import hr.datastock.MockEntityDataValues;
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
    class FirmeServiceTestGetAllCompanies {

        @Test
        @DisplayName("GIVEN firma records exists in database, WHEN all firma records are requested, THEN all firma records from database are returned.")
        void testGetAll() {
            final List<FirmeEntity> expectedList = MockEntityDataValues.givenFirmeDataRecords();

            when(firmeRepository.findAll()).thenReturn(MockEntityDataValues.givenFirmeDataRecords());
            final List<FirmeEntity> actualList = firmeService.getAll();

            assertAll(
                    () -> assertNotNull(actualList),
                    () -> assertEquals(expectedList, actualList));
        }

        @Test
        @DisplayName("GIVEN there are no firma records in database, WHEN all firma records are requested, THEN empty list is returned.")
        void testGetAllEmpty() {
            final List<FirmeEntity> expectedListOfFirma = Collections.emptyList();

            when(firmeRepository.findAll()).thenReturn(Collections.emptyList());
            final List<FirmeEntity> actualListOfFirma = firmeService.getAll();

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
        void testGetOneById() {
            final FirmeEntity expectedFirma = MockEntityDataValues.givenFirmeDataRecords().get(0);

            when(firmeRepository.findById(1L))
                    .thenReturn(MockEntityDataValues.givenFirmeDataRecords()
                            .stream().filter(firmeEntity -> firmeEntity.getIdFirme() == 1L).findFirst());
            final FirmeEntity actualFirma = firmeService.getOneById(1L);

            assertAll(
                    () -> assertNotNull(actualFirma),
                    () -> assertEquals(expectedFirma, actualFirma)
            );
        }

        @Test
        @DisplayName("GIVEN firma record does not exists in database, WHEN a single firma record is fetched, THEN error is thrown.")
        void testGetOneByIdNonExistingId() {
            Class<FirmeEntityNotFoundRuntimeException> expectedExceptionClass = FirmeEntityNotFoundRuntimeException.class;

            when(firmeRepository.findById(150L)).thenThrow(new FirmeEntityNotFoundRuntimeException(150L));
            Executable executable = () -> firmeService.getOneById(150L);

            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Nested
    @DisplayName("FirmeService create firma")
    class FirmeServiceTestCreateFirma {

        @Test
        @DisplayName("GIVEN firma record does not exist in database, WHEN new firma record is created, THEN new record is created and returned.")
        void testCreateFirma() {
            final FirmeEntity expectedFirma = MockEntityDataValues.givenFirmeDataRecords().get(5);

            when(firmeRepository.save(any(FirmeEntity.class)))
                    .thenReturn(expectedFirma);
            when(firmeRepository.checkIfExistingOibIsInTableFirme(expectedFirma))
                    .thenReturn(Collections.emptyList());

            final FirmeEntity actualFirma = firmeService.createFirma(expectedFirma);

            assertAll(
                    () -> assertNotNull(actualFirma),
                    () -> assertEquals(expectedFirma, actualFirma)
            );
        }

        @Test
        @DisplayName("GIVEN firma record exists in database, WHEN new firma record is created, THEN error is thrown.")
        void testCreateFirmaExistingRecord() {
            final FirmeEntity expectedFirma = MockEntityDataValues.givenFirmeDataRecords().get(3);
            Class<FirmeEntityExistsRuntimeException> expectedExceptionClass = FirmeEntityExistsRuntimeException.class;

            when(firmeRepository.checkIfExistingOibIsInTableFirme(expectedFirma))
                    .thenReturn(MockEntityDataValues.givenFirmeDataRecords().subList(0, 1));

            Executable executable = () -> firmeService.createFirma(expectedFirma);

            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Nested
    @DisplayName("FirmeService update firma")
    class FirmeServiceTestUpdateExistingFirma {

        @Test
        @DisplayName("GIVEN firma record exists in database, WHEN a firma record is updated, THEN firma record is updated and returned.")
        void testUpdateExistingFirma() {
            final FirmeEntity expectedFirmaWithUpdates = MockEntityDataValues.givenFirmeDataRecords().get(2);

            when(firmeRepository.findById(1L))
                    .thenReturn(MockEntityDataValues.givenFirmeDataRecords().stream().filter(firmeEntity -> firmeEntity.getIdFirme() == 1L).findFirst());
            when(firmeRepository.save(any(FirmeEntity.class))).thenReturn(expectedFirmaWithUpdates);
            when(firmeRepository.checkIfExistingOibIsInTableFirme(new FirmeEntity(1L, "02013025652", "KiloByte")))
                    .thenReturn(Collections.emptyList());

            final FirmeEntity updatedFirmaEntity = firmeService.updateExistingFirma(expectedFirmaWithUpdates, 1L);

            assertAll(
                    () -> assertNotNull(updatedFirmaEntity),
                    () -> assertEquals(expectedFirmaWithUpdates, updatedFirmaEntity)
            );
        }

        @Test
        @DisplayName("GIVEN firma record does not exist in database, WHEN a firma record is updated, THEN error is thrown.")
        void testUpdateNonExistingFirma() {
            final FirmeEntity existingFirmaWithUpdates = MockEntityDataValues.givenFirmeDataRecords().get(4);
            Class<FirmeEntityNotFoundRuntimeException> expectedExceptionClass = FirmeEntityNotFoundRuntimeException.class;

            when(firmeRepository.findById(1L))
                    .thenThrow(expectedExceptionClass);

            Executable executable = () -> firmeService.updateExistingFirma(existingFirmaWithUpdates, 1L);

            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Nested
    @DisplayName("FirmeService update firma")
    class FirmeServiceTestDeleteFirma {
        @Test
        @DisplayName("GIVEN firma record either exist or not, WHEN a single firma record is deleted, THEN repository delete method should be called once.")
        void testDeleteFirma() {
            final FirmeEntity firma = MockEntityDataValues.givenFirmeDataRecords().get(0);

            firmeRepository.delete(firma);

            verify(firmeRepository, times(1)).delete(firma);
        }
    }
}